 public void send(byte[] data, int length, InetAddress host, int port)
 throws IOException
    {
 _sendPacket.setData(data);
 _sendPacket.setLength(length);
 _sendPacket.setAddress(host);
 _sendPacket.setPort(port);
 _socket_.send(_sendPacket);
    }