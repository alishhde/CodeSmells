 private static String normalizePath(String path) {
 // count the number of '/'s, to determine number of segments
 int index = -1;
 int pathlen = path.length();
 int size = 0;
 if (pathlen > 0 && path.charAt(0) != '/') {
 size++;
    }
 while ((index = path.indexOf('/', index + 1)) != -1) {
 if (index + 1 < pathlen && path.charAt(index + 1) != '/') {
 size++;
      }
    }


 String[] seglist = new String[size];
 boolean[] include = new boolean[size];


 // break the path into segments and store in the list
 int current = 0;
 int index2 = 0;
 index = (pathlen > 0 && path.charAt(0) == '/') ? 1 : 0;
 while ((index2 = path.indexOf('/', index + 1)) != -1) {
 seglist[current++] = path.substring(index, index2);
 index = index2 + 1;
    }


 // if current==size, then the last character was a slash
 // and there are no more segments
 if (current < size) {
 seglist[current] = path.substring(index);
    }


 // determine which segments get included in the normalized path
 for (int i = 0; i < size; i++) {
 include[i] = true;
 if (seglist[i].equals("..")) { //$NON-NLS-1$
 int remove = i - 1;
 // search back to find a segment to remove, if possible
 while (remove > -1 && !include[remove]) {
 remove--;
        }
 // if we find a segment to remove, remove it and the ".."
 // segment
 if (remove > -1 && !seglist[remove].equals("..")) { //$NON-NLS-1$
 include[remove] = false;
 include[i] = false;
        }
      } else if (seglist[i].equals(".")) { //$NON-NLS-1$
 include[i] = false;
      }
    }


 // put the path back together
 StringBuilder newpath = new StringBuilder();
 if (path.startsWith("/")) { //$NON-NLS-1$
 newpath.append('/');
    }


 for (int i = 0; i < seglist.length; i++) {
 if (include[i]) {
 newpath.append(seglist[i]);
 newpath.append('/');
      }
    }


 // if we used at least one segment and the path previously ended with
 // a slash and the last segment is still used, then delete the extra
 // trailing '/'
 if (!path.endsWith("/") && seglist.length > 0 //$NON-NLS-1$
        && include[seglist.length - 1]) {
 newpath.deleteCharAt(newpath.length() - 1);
    }


 String result = newpath.toString();


 // check for a ':' in the first segment if one exists,
 // prepend "./" to normalize
 index = result.indexOf(':');
 index2 = result.indexOf('/');
 if (index != -1 && (index < index2 || index2 == -1)) {
 newpath.insert(0, "./"); //$NON-NLS-1$
 result = newpath.toString();
    }
 return result;
  }