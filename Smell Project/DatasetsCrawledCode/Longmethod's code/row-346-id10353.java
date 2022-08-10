 private Map<String, String> tika_parse(InputStream sourceStream, String prefix, Integer maxAttribs,
 Integer maxAttribLen) throws IOException, TikaException, SAXException {
 final Metadata metadata = new Metadata();
 final TikaInputStream tikaInputStream = TikaInputStream.get(sourceStream);
 try {
 autoDetectParser.parse(tikaInputStream, new DefaultHandler(), metadata);
        } finally {
 tikaInputStream.close();
        }


 final Map<String, String> results = new HashMap<>();
 final Pattern metadataKeyFilter = metadataKeyFilterRef.get();
 final StringBuilder dataBuilder = new StringBuilder();
 for (final String key : metadata.names()) {
 if (metadataKeyFilter != null && !metadataKeyFilter.matcher(key).matches()) {
 continue;
            }
 dataBuilder.setLength(0);
 if (metadata.isMultiValued(key)) {
 for (String val : metadata.getValues(key)) {
 if (dataBuilder.length() > 1) {
 dataBuilder.append(", ");
                    }
 if (dataBuilder.length() + val.length() < maxAttribLen) {
 dataBuilder.append(val);
                    } else {
 dataBuilder.append("...");
 break;
                    }
                }
            } else {
 dataBuilder.append(metadata.get(key));
            }
 if (prefix == null) {
 results.put(key, dataBuilder.toString().trim());
            } else {
 results.put(prefix + key, dataBuilder.toString().trim());
            }


 // cutoff at max if provided
 if (maxAttribs != null && results.size() >= maxAttribs) {
 break;
            }
        }
 return results;
    }