package client;

public class ServerInfo {
    private static String[] ip = new String[4]; 
    private static int port = 0;
   
    public static String[] getIp() {
        return ip;
    }
    public static int getPort() {
        return port;
    }

    public static void setIp(String num) {
        String tmp = "";
        int del = 0;
        for (int i = 0 ; i<num.length() ; i++) {
            if (num.charAt(i) == '.') {
                del += 1;
                if (del > 3) {
                    System.out.println("Wrong format for ip");
                    return;
                }
                if (tmp.length() == 0) {
                    System.out.println("Wrong format for ip");
                    return;           
                }
                ip[del-1] = tmp;
            }
            if (Character.isDigit(num.charAt(i))) {
                tmp += num.charAt(i);
            }
            if (!Character.isDigit(num.charAt(i)) && num.charAt(i) != '.') {
                System.out.println("Wrong format for ip");
                return;      
            }
        } 
    }

    public static void setPort(int p) {
        port = p;
    }
}
