 public <T extends Object> List<NamedSerializationContexts<T>> getNamedContexts(final SerializationContextMap<T> map) {
 final ArrayList<NamedSerializationContexts<T>> result = CollectionLiterals.<NamedSerializationContexts<T>>newArrayList();
 final HashMap<String, Integer> names = CollectionLiterals.<String, Integer>newHashMap();
 List<SerializationContextMap.Entry<T>> _values = map.values();
 for (final SerializationContextMap.Entry<T> e : _values) {
 Set<EClass> _types = e.getTypes();
 for (final EClass t : _types) {
        {
 final List<ISerializationContext> ctx = e.getContexts(t);
 String _xifexpression = null;
 if ((t == null)) {
 _xifexpression = "";
          } else {
 _xifexpression = t.getName();
          }
 String _plus = (_xifexpression + "_");
 String _significantGrammarElement = this.getSignificantGrammarElement(ctx);
 final String name = (_plus + _significantGrammarElement);
 final Integer dup = names.get(name);
 String _xifexpression_1 = null;
 if ((dup == null)) {
 String _xblockexpression = null;
            {
 names.put(name, Integer.valueOf(1));
 _xblockexpression = name;
            }
 _xifexpression_1 = _xblockexpression;
          } else {
 String _xblockexpression_1 = null;
            {
 names.put(name, Integer.valueOf(((dup).intValue() + 1)));
 _xblockexpression_1 = ((name + "_") + dup);
            }
 _xifexpression_1 = _xblockexpression_1;
          }
 final String unique = _xifexpression_1;
 T _value = e.getValue();
 NamedSerializationContexts<T> _namedSerializationContexts = new NamedSerializationContexts<T>(unique, t, ctx, _value);
 result.add(_namedSerializationContexts);
        }
      }
    }
 return result;
  }