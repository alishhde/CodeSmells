 public static String getOatFileInstructionSet(File oatFile) throws Throwable {
 ShareElfFile elfFile = null;
 String result = "";
 try {
 elfFile = new ShareElfFile(oatFile);
 final ShareElfFile.SectionHeader roDataHdr = elfFile.getSectionHeaderByName(".rodata");
 if (roDataHdr == null) {
 throw new IOException("Unable to find .rodata section.");
            }


 final FileChannel channel = elfFile.getChannel();
 channel.position(roDataHdr.shOffset);


 final byte[] oatMagicAndVersion = new byte[8];
 ShareElfFile.readUntilLimit(channel, ByteBuffer.wrap(oatMagicAndVersion), "Failed to read oat magic and version.");


 if (oatMagicAndVersion[0] != 'o'
                    || oatMagicAndVersion[1] != 'a'
                    || oatMagicAndVersion[2] != 't'
                    || oatMagicAndVersion[3] != '\n') {
 throw new IOException(
 String.format("Bad oat magic: %x %x %x %x",
 oatMagicAndVersion[0],
 oatMagicAndVersion[1],
 oatMagicAndVersion[2],
 oatMagicAndVersion[3])
                );
            }


 final int versionOffsetFromOatBegin = 4;
 final int versionBytes = 3;


 final String oatVersion = new String(oatMagicAndVersion,
 versionOffsetFromOatBegin, versionBytes, Charset.forName("ASCII"));
 try {
 Integer.parseInt(oatVersion);
            } catch (NumberFormatException e) {
 throw new IOException("Bad oat version: " + oatVersion);
            }


 ByteBuffer buffer = ByteBuffer.allocate(128);
 buffer.order(elfFile.getDataOrder());
 // TODO This is a risk point, since each oat version may use a different offset.
 // So far it's ok. Perhaps we should use oatVersionNum to judge the right offset in
 // the future.
 final int isaNumOffsetFromOatBegin = 12;
 channel.position(roDataHdr.shOffset + isaNumOffsetFromOatBegin);
 buffer.limit(4);
 ShareElfFile.readUntilLimit(channel, buffer, "Failed to read isa num.");


 int isaNum = buffer.getInt();
 if (isaNum < 0 || isaNum >= InstructionSet.values().length) {
 throw new IOException("Bad isa num: " + isaNum);
            }


 switch (InstructionSet.values()[isaNum]) {
 case kArm:
 case kThumb2:
 result = "arm";
 break;
 case kArm64:
 result = "arm64";
 break;
 case kX86:
 result = "x86";
 break;
 case kX86_64:
 result = "x86_64";
 break;
 case kMips:
 result = "mips";
 break;
 case kMips64:
 result = "mips64";
 break;
 case kNone:
 result = "none";
 break;
 default:
 throw new IOException("Should not reach here.");
            }
        } finally {
 if (elfFile != null) {
 try {
 elfFile.close();
                } catch (Exception ignored) {
 // Ignored.
                }
            }
        }
 return result;
    }