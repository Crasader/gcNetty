package gcNetty;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * netty client
 * @author chenl
 *
 */

public class NettyClient {
	public static void main(String[] args) {
		ClientBootstrap server=new ClientBootstrap(new NioClientSocketChannelFactory());
		server.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new HelloServerHandler());  
			}
		});
		server.connect(new InetSocketAddress("127.0.0.1",8888));
	}
	
	private static class HelloServerHandler extends SimpleChannelHandler{
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			System.out.println("Hello world, I'm client.");
			Scanner sc=new Scanner(System.in);
			while(true){
				String input=sc.nextLine();
				System.out.println("client：======================");
				ChannelBuffer cb=ChannelBuffers.buffer(input.getBytes().length);
				cb.writeBytes(input.getBytes());
				e.getChannel().write(cb);
			}
		}
	}
	
}
