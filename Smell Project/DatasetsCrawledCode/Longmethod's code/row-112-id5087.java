 @Override
 public Sampler deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
 JsonNode node = jp.getCodec().readTree(jp);
 String type = node.get("type").asText();
 switch (type) {
 case "uniform":
          {
 double lowerBound = node.get("lower").asDouble();
 double upperBound = node.get("upper").asDouble();
 checkArgument(
 lowerBound >= 0,
 "The lower bound of uniform distribution should be a non-negative number, "
                    + "but found %s.",
 lowerBound);
 return fromRealDistribution(new UniformRealDistribution(lowerBound, upperBound));
          }
 case "exp":
          {
 double mean = node.get("mean").asDouble();
 return fromRealDistribution(new ExponentialDistribution(mean));
          }
 case "normal":
          {
 double mean = node.get("mean").asDouble();
 double stddev = node.get("stddev").asDouble();
 checkArgument(
 mean >= 0,
 "The mean of normal distribution should be a non-negative number, but found %s.",
 mean);
 return fromRealDistribution(new NormalDistribution(mean, stddev));
          }
 case "const":
          {
 double constant = node.get("const").asDouble();
 checkArgument(
 constant >= 0,
 "The value of constant distribution should be a non-negative number, but found %s.",
 constant);
 return fromRealDistribution(new ConstantRealDistribution(constant));
          }
 case "zipf":
          {
 double param = node.get("param").asDouble();
 final double multiplier =
 node.has("multiplier") ? node.get("multiplier").asDouble() : 1.0;
 checkArgument(
 param > 1,
 "The parameter of the Zipf distribution should be > 1, but found %s.",
 param);
 checkArgument(
 multiplier >= 0,
 "The multiplier of the Zipf distribution should be >= 0, but found %s.",
 multiplier);
 final ZipfDistribution dist = new ZipfDistribution(100, param);
 return scaledSampler(fromIntegerDistribution(dist), multiplier);
          }
 default:
          {
 throw new IllegalArgumentException("Unknown distribution type: " + type);
          }
      }
    }