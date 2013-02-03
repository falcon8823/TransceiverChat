package net.falconsrv.tranchat;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BcastGetter {
	// ブロードキャストアドレスリストを取得するメソッド
	public static List<String> GetBcastAddr() throws SocketException {
		// NICの一覧を取得
		Enumeration<NetworkInterface> nic_list = NetworkInterface.getNetworkInterfaces();

		List<String> addr_list = new ArrayList<String>();

		addr_list.add("255.255.255.255");

		if (null != nic_list)
		{
			while (nic_list.hasMoreElements())
			{
				NetworkInterface nic = (NetworkInterface)nic_list.nextElement();
				List<InterfaceAddress> enuAddrs = nic.getInterfaceAddresses();

				// 各NICに割り当てられているアドレスについて
				for(InterfaceAddress addr : enuAddrs){
					InetAddress baddr = addr.getBroadcast();
					// baddrがnullのときはIPv6アドレスなので読み捨て
					if(baddr != null) addr_list.add(baddr.getHostAddress());
				}
			}
		}

		return addr_list;
	}
}
