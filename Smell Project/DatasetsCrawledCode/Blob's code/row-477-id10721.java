 private class Interleaved {


 private char[] entries = null; // <ngram(length chars)><count(2 chars)>*
 private int size = 0; // Number of entries (one entry = length+2 chars)
 private long entriesGeneratedAtCount = -1; // Keeps track of when the sequential structure was current


 /**
         * Ensure that the entries array is in sync with the ngrams.
         */
 public void update() {
 if (count == entriesGeneratedAtCount) { // Already up to date
 return;
            }
 size = ngrams.size();
 final int numChars = (length+2)*size;
 if (entries == null || entries.length < numChars) {
 entries = new char[numChars];
            }
 int pos = 0;
 for (Map.Entry<String, Counter> entry: getSortedNgrams()) {
 for (int l = 0 ; l < length ; l++) {
 entries[pos + l] = entry.getKey().charAt(l);
                }
 entries[pos + length] = (char)(entry.getValue().count / 65536); // Upper 16 bit
 entries[pos + length + 1] = (char)(entry.getValue().count % 65536); // lower 16 bit
 pos += length + 2;
            }
 entriesGeneratedAtCount = count;
        }


 public Entry firstEntry() {
 Entry entry = new Entry();
 if (size > 0) {
 entry.update(0);
            }
 return entry;
        }
 
 private List<Map.Entry<String, Counter>> getSortedNgrams() {
 List<Map.Entry<String, Counter>> entries = new ArrayList<Map.Entry<String, Counter>>(ngrams.size());
 entries.addAll(ngrams.entrySet());
 Collections.sort(entries, new Comparator<Map.Entry<String, Counter>>() {
 @Override
 public int compare(Map.Entry<String, Counter> o1, Map.Entry<String, Counter> o2) {
 return o1.getKey().compareTo(o2.getKey());
                }
            });
 return entries;
        }
 
 private class Entry implements Comparable<Entry> {
 char[] ngram = new char[length];
 int count = 0;
 int pos = 0;


 private void update(int pos) {
 this.pos = pos;
 if (pos >= size) { // Reached the end
 return;
                }
 final int origo = pos*(length+2);
 System.arraycopy(entries, origo, ngram, 0, length);
 count = entries[origo+length] * 65536 + entries[origo+length+1];
            }


 @Override
 public int compareTo(Entry other) {
 for (int i = 0 ; i < ngram.length ; i++) {
 if (ngram[i] != other.ngram[i]) {
 return ngram[i] - other.ngram[i];
                    }
                }
 return 0;
            }
 public boolean hasNext() {
 return pos < size-1;
            }
 public boolean hasNgram() {
 return pos < size;
            }
 public void next() {
 update(pos+1);
            }
 public String toString() {
 return new String(ngram) + "(" + count + ")";
            }
        }
    }