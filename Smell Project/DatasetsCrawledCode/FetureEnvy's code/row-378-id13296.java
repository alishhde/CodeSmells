 void getHashes(List<Integer> searchHashes, int bitShift)
  {
 // We don't need to include 0 because that's always assumed in look ups. If we do return 0, that
 // means this agent isn't sure what it needs, but the inverse is acceptable because that just
 // means the airing doesn't know what it matches and it will be tested on all of the agents.
 searchHashes.clear();


 if (title != null)
    {
 searchHashes.add((title.ignoreCaseHash >>> bitShift));
    }


 if (person != null)
    {
 addHash(person.ignoreCaseHash, searchHashes, bitShift);
    }


 if (category != null)
    {
 addHash(category.ignoreCaseHash, searchHashes, bitShift);
    }


 if (subCategory != null)
    {
 addHash(subCategory.ignoreCaseHash, searchHashes, bitShift);
    }


 if (chanName.length() > 0)
    {
 addHash(chanName.hashCode(), searchHashes, bitShift);
    }


 if (chanNames != null && chanNames.length > 0)
    {
 for (String chanName : chanNames)
      {
 addHash(chanName.hashCode(), searchHashes, bitShift);
      }
    }


 if (network != null)
    {
 addHash(network.ignoreCaseHash, searchHashes, bitShift);
    }


 if (rated != null)
    {
 addHash(rated.ignoreCaseHash, searchHashes, bitShift);
    }


 if (year != null)
    {
 addHash(year.ignoreCaseHash, searchHashes, bitShift);
    }


 if (pr != null)
    {
 addHash(pr.ignoreCaseHash, searchHashes, bitShift);
    }


 // This will ensure that we do a full search since 0 means at least one of our items doesn't
 // have a "valid" hash.
 if (searchHashes.contains(0))
 searchHashes.clear();
  }