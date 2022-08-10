 private void processSingleRecord(final Record record) {


 String data = null;
 final ObjectMapper mapper = new ObjectMapper();
 try {
 final ByteBuffer buffer = record.getData();
 data = new String(buffer.array(), "UTF-8");
 final RekognitionOutput output = mapper.readValue(data, RekognitionOutput.class);


 // Get the fragment number from Rekognition Output
 final String fragmentNumber = output
                    .getInputInformation()
                    .getKinesisVideo()
                    .getFragmentNumber();
 final Double frameOffsetInSeconds = output
                    .getInputInformation()
                    .getKinesisVideo()
                    .getFrameOffsetInSeconds();
 final Double serverTimestamp = output
                    .getInputInformation()
                    .getKinesisVideo()
                    .getServerTimestamp();
 final Double producerTimestamp = output
                    .getInputInformation()
                    .getKinesisVideo()
                    .getProducerTimestamp();
 final double detectedTime = output.getInputInformation().getKinesisVideo().getServerTimestamp()
                    + output.getInputInformation().getKinesisVideo().getFrameOffsetInSeconds() * 1000L;
 final RekognizedOutput rekognizedOutput = RekognizedOutput.builder()
                    .fragmentNumber(fragmentNumber)
                    .serverTimestamp(serverTimestamp)
                    .producerTimestamp(producerTimestamp)
                    .frameOffsetInSeconds(frameOffsetInSeconds)
                    .detectedTime(detectedTime)
                    .build();


 // Add face search response
 final List<FaceSearchResponse> responses = output.getFaceSearchResponse();


 responses.forEach(response -> {
 final DetectedFace detectedFace = response.getDetectedFace();
 final List<MatchedFace> matchedFaces = response.getMatchedFaces();
 final RekognizedOutput.FaceSearchOutput faceSearchOutput = RekognizedOutput.FaceSearchOutput.builder()
                        .detectedFace(detectedFace)
                        .matchedFaceList(matchedFaces)
                        .build();
 rekognizedOutput.addFaceSearchOutput(faceSearchOutput);
            });


 // Add it to the index
 log.info("Found Rekognized results for fragment number : {}", fragmentNumber);
 rekognizedFragmentsIndex.add(fragmentNumber, producerTimestamp.longValue(),
 serverTimestamp.longValue(), rekognizedOutput);


        } catch (final NumberFormatException e) {
 log.warn("Record does not match sample record format. Ignoring record with data : {}", data, e);
        } catch (final Exception e) {
 log.error("Unable to process record !", e);
        }
    }