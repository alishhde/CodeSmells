 public static RuleSet parse(final Reader configReader, EventLoggerProvider eventLogger)
    {
 RuleSetCreator ruleSetCreator = new RuleSetCreator();


 int line = 0;
 try(Reader fileReader = configReader)
        {
 LOGGER.debug("About to load ACL file");
 StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(fileReader));
 tokenizer.resetSyntax(); // setup the tokenizer


 tokenizer.commentChar(COMMENT); // single line comments
 tokenizer.eolIsSignificant(true); // return EOL as a token
 tokenizer.ordinaryChar('='); // equals is a token
 tokenizer.ordinaryChar(CONTINUATION); // continuation character (when followed by EOL)
 tokenizer.quoteChar('"'); // double quote
 tokenizer.quoteChar('\''); // single quote
 tokenizer.whitespaceChars('\u0000', '\u0020'); // whitespace (to be ignored) TODO properly
 tokenizer.wordChars('a', 'z'); // unquoted token characters [a-z]
 tokenizer.wordChars('A', 'Z'); // [A-Z]
 tokenizer.wordChars('0', '9'); // [0-9]
 tokenizer.wordChars('_', '_'); // underscore
 tokenizer.wordChars('-', '-'); // dash
 tokenizer.wordChars('.', '.'); // dot
 tokenizer.wordChars('*', '*'); // star
 tokenizer.wordChars('@', '@'); // at
 tokenizer.wordChars(':', ':'); // colon


 // parse the acl file lines
 Stack<String> stack = new Stack<>();
 int current;
 do {
 current = tokenizer.nextToken();
 line = tokenizer.lineno()-1;
 switch (current)
                {
 case StreamTokenizer.TT_EOF:
 case StreamTokenizer.TT_EOL:
 if (stack.isEmpty())
                        {
 break; // blank line
                        }


 // pull out the first token from the bottom of the stack and check arguments exist
 String first = stack.firstElement();
 stack.removeElementAt(0);
 if (stack.isEmpty())
                        {
 throw new IllegalConfigurationException(String.format(NOT_ENOUGH_TOKENS_MSG, line));
                        }


 // check for and parse optional initial number for ACL lines
 Integer number = null;
 if (first != null && first.matches("\\d+"))
                        {
 // set the acl number and get the next element
 number = Integer.valueOf(first);
 first = stack.firstElement();
 stack.removeElementAt(0);
                        }


 if (ACL.equalsIgnoreCase(first))
                        {
 parseAcl(number, stack, ruleSetCreator, line);
                        }
 else if (number == null)
                        {
 if("GROUP".equalsIgnoreCase(first))
                            {
 throw new IllegalConfigurationException(String.format("GROUP keyword not supported at "
                                                                                      + "line %d. Groups should defined "
                                                                                      + "via a Group Provider, not in "
                                                                                      + "the ACL file.",
 line));
                            }
 else if (CONFIG.equalsIgnoreCase(first))
                            {
 parseConfig(stack, ruleSetCreator, line);
                            }
 else
                            {
 throw new IllegalConfigurationException(String.format(UNRECOGNISED_INITIAL_MSG, first, line));
                            }
                        }
 else
                        {
 throw new IllegalConfigurationException(String.format(NUMBER_NOT_ALLOWED_MSG, first, line));
                        }


 // reset stack, start next line
 stack.clear();
 break;
 case StreamTokenizer.TT_NUMBER:
 stack.push(Integer.toString(Double.valueOf(tokenizer.nval).intValue()));
 break;
 case StreamTokenizer.TT_WORD:
 stack.push(tokenizer.sval); // token
 break;
 default:
 if (tokenizer.ttype == CONTINUATION)
                        {
 int next = tokenizer.nextToken();
 line = tokenizer.lineno()-1;
 if (next == StreamTokenizer.TT_EOL)
                            {
 break; // continue reading next line
                            }


 // invalid location for continuation character (add one to line because we ate the EOL)
 throw new IllegalConfigurationException(String.format(PREMATURE_CONTINUATION_MSG, line + 1));
                        }
 else if (tokenizer.ttype == '\'' || tokenizer.ttype == '"')
                        {
 stack.push(tokenizer.sval); // quoted token
                        }
 else
                        {
 stack.push(Character.toString((char) tokenizer.ttype)); // single character
                        }
                }
            } while (current != StreamTokenizer.TT_EOF);


 if (!stack.isEmpty())
            {
 throw new IllegalConfigurationException(String.format(PREMATURE_EOF_MSG, line));
            }
        }
 catch (IllegalArgumentException iae)
        {
 throw new IllegalConfigurationException(String.format(PARSE_TOKEN_FAILED_MSG, line), iae);
        }
 catch (IOException ioe)
        {
 throw new IllegalConfigurationException(CANNOT_LOAD_MSG, ioe);
        }
 return ruleSetCreator.createRuleSet(eventLogger);
    }