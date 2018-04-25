package gcNetty;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.jboss.netty.bootstrap.ServerBootstrap;
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
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


/**
 * netty server
 * @author chenl
 *
 */
public class NettyServer {
	public static void main(String[] args) {
		ServerBootstrap server=new ServerBootstrap(new NioServerSocketChannelFactory());
		server.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new HelloServerHandler(),new HelloServerUpStreamHandler());  
			}
		});
		server.bind(new InetSocketAddress(8888));
	}
	
	private static class HelloServerHandler extends SimpleChannelHandler{
		@Override
		public void channelConnected(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			System.out.println("Hello world, I'm server.");
		}
	}
	
	private static class HelloServerUpStreamHandler extends SimpleChannelUpstreamHandler{
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			ChannelBuffer cb=(ChannelBuffer) e.getMessage();
			System.out.println(cb.toString(Charset.defaultCharset()));
		}
	}
}
