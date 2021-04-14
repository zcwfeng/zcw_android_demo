package top.zcwfeng.android_okhttp.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public
class HttpDns implements Dns {
    private static final Dns SYSTEM = Dns.SYSTEM;

    @NotNull
    @Override
    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {
        Log.e("HttpDns", "lookup:" + hostname);
        String ip = DNSHelper.getIpByHost(hostname);
        if (ip != null && !ip.equals("")) {
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            Log.e("HttpDns", "inetAddresses:" + inetAddresses);
            return inetAddresses;
        }

        if (hostname == null) {
            throw new UnknownHostException("hostname == null");
        } else {
            try {
                List<InetAddress> mInetAddressesList = new ArrayList<>();
                InetAddress[] mInetAddresses = InetAddress.getAllByName(hostname);
//                LogUtil.d("DNSAddress","ips:"+mInetAddresses.length);
                StringBuilder ips = new StringBuilder();
                try{
                    if(mInetAddresses!=null&&mInetAddresses.length>1){
                        for (InetAddress address : mInetAddresses) {
                            if (address instanceof Inet4Address) {
                                ips.append("IPV4===>"+address.getHostAddress()+","+address.getHostName()+","+address.getCanonicalHostName()+","+address.getCanonicalHostName()+"\n");
                            } else {
                                ips.append("IPV6===>"+address.getHostAddress()+","+address.getHostName()+","+address.getCanonicalHostName()+","+address.getCanonicalHostName()+"\n");
                            }
                        }
                        throw new IPException(ips.toString());
                    }else if(mInetAddresses!=null){
                        for (InetAddress address : mInetAddresses) {
                            if (!(address instanceof Inet4Address)) {
                                ips.append("IPV6===>"+address.getHostAddress()+","+address.getHostName()+","+address.getCanonicalHostName()+","+address.getCanonicalHostName()+"\n");
                            }
                            throw new IPException(ips.toString());
                        }
                    }

                }catch (IPException e){
//                    UMCrash.generateCustomLog(e.getMessage(),"IPPARSEException");
                }

                for (InetAddress address : mInetAddresses) {
                    if (address instanceof Inet4Address) {
                        mInetAddressesList.add(0, address);
                    } else {
                        mInetAddressesList.add(address);
                    }
                }
                return mInetAddressesList;
            } catch (NullPointerException var4) {
                UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour");
                unknownHostException.initCause(var4);
                throw unknownHostException;
            }
        }
        return SYSTEM.lookup(hostname);
    }
}


public static class IPException extends Exception{
    public IPException(String message) {
        super(message);
    }
}