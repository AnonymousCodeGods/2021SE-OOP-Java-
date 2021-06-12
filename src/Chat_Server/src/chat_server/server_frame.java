package chat_server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * 
 * @author 李彧
 * @param clientOutputStreams
 * @param ip_addr_val 服务端ip地址
 * @param port_val 服务端端口值
 */

public class server_frame extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;
   ArrayList<String> users;
   String ip_addr_val;
   int port_val;

   /**
    * 用于处理每个用户发送过来的数据流，通过用户传输的信息在服务端控制界面进行输出，并将信息发送给
    * 每个用户。由于用户有多位，所以采用Runnable接口实现多线程操作。
    */
   public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ta_chat.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        tellEveryone(message);
                    } 
                    else 
                    {
                        ta_chat.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }
    
    /**
     * 用户界面ui组件初始化。
     */
    public server_frame() 
    {
        initComponents();
        //init();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        server_icon = new javax.swing.JLabel();
        lb_ip = new javax.swing.JLabel();
        tf_ip_val = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_port_val = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Server's frame");
        setName("server"); // NOI18N
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_users.setText("Online Users");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        server_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/server.png"))); // NOI18N

        lb_ip.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        lb_ip.setText("IP Address");

        tf_ip_val.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        tf_ip_val.setForeground(new java.awt.Color(204, 204, 204));
        tf_ip_val.setText("(Default:localhost)");
        tf_ip_val.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_ip_valActionPerformed(evt);
            }
        });

        lb_port.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        lb_port.setText("Port");

        tf_port_val.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        tf_port_val.setForeground(new java.awt.Color(204, 204, 204));
        tf_port_val.setText("(Default:2222)");
        tf_port_val.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_port_valActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60)
                        .addComponent(server_icon)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_ip)
                            .addComponent(lb_port))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_ip_val, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(tf_port_val))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_start)
                            .addComponent(b_users))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_clear)
                            .addComponent(b_end)))
                    .addComponent(server_icon)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_ip)
                            .addComponent(tf_ip_val, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_port)
                            .addComponent(tf_port_val, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 用户点击End按钮后，退出服务端界面。
     * @param evt 点击事件。
     */
    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try 
        {
            Thread.sleep(5000);                 
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");
        
        ta_chat.setText("");
        System.exit(0);
    }//GEN-LAST:event_b_endActionPerformed

    /**
     * 用户点击Start按钮后开启服务端
     * @param evt 点击事件
     */
    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Server started...\n");
    }//GEN-LAST:event_b_startActionPerformed

    /**
     * 用户点击Online Users显示在线用户
     * @param evt 点击事件
     */
    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        ta_chat.append("\n Online users : \n");
        for (String current_user : users)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        
    }//GEN-LAST:event_b_usersActionPerformed

    /**
     * 用户点击clear按钮清楚当前服务端界面信息。
     * @param evt 点击事件
     */
    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    private void tf_ip_valActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_ip_valActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_ip_valActionPerformed

    private void tf_port_valActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_port_valActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_port_valActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new server_frame().setVisible(true);
            }
        });
    }
    
    /**
     * 用户点击Start按钮后创建ServerSocket并等待用户socket的到来。
     */
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();
            if(tf_ip_val.getText().contains("localhost"))
            {
                ip_addr_val = "localhost";
            }
            else {
                if(validIPAddress(tf_ip_val.getText()).equals("Neither"))
                    JOptionPane.showMessageDialog(server_frame.this, "Invalid IP address!");
                else
                    ip_addr_val = tf_ip_val.getText();
            }
            if(tf_port_val.getText().contains("Default:2222"))
                port_val = 2222;
            else {
                if(isNumeric(tf_port_val.getText()) && tf_port_val.getText().trim().length() == 4)
                    port_val = Integer.parseInt(tf_port_val.getText());
                else
                    JOptionPane.showMessageDialog(server_frame.this, "Please enter port as a 4 digits integer!");
            }

            try 
            {
                InetAddress addr = InetAddress.getByName(ip_addr_val);
                ServerSocket serverSock = new ServerSocket(port_val, 100, addr);
                //ServerSocket serverSock = new ServerSocket(2222);

                while (true) 
                {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    ta_chat.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }
    
    /**
     * 用户连接成功后加入用户列表
     * @param data 用户名
     */
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before " + name + " added. \n");
        users.add(name);
        ta_chat.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    /**
     * 用户从用户列表移除
     * @param data 用户名
     */
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    /**
     * 接受用户信息后向每个用户输出某个用户发送的消息。
     * @param message 需要向每个用户发送的消息。
     */
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		ta_chat.append("Sending: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("Error telling everyone. \n");
            }
        } 
    }
    
    public String validateIPv4(String IP) {
    String[] nums = IP.split("\\.", -1);
    for (String x : nums) {
      // Validate integer in range (0, 255):
      // 1. length of chunk is between 1 and 3
      if (x.length() == 0 || x.length() > 3) return "Neither";
      // 2. no extra leading zeros
      if (x.charAt(0) == '0' && x.length() != 1) return "Neither";
      // 3. only digits are allowed
      for (char ch : x.toCharArray()) {
        if (! Character.isDigit(ch)) return "Neither";
      }
      // 4. less than 255
      if (Integer.parseInt(x) > 255) return "Neither";
    }
    return "IPv4";
  }

  public String validateIPv6(String IP) {
    String[] nums = IP.split(":", -1);
    String hexdigits = "0123456789abcdefABCDEF";
    for (String x : nums) {
      // Validate hexadecimal in range (0, 2**16):
      // 1. at least one and not more than 4 hexdigits in one chunk
      if (x.length() == 0 || x.length() > 4) return "Neither";
      // 2. only hexdigits are allowed: 0-9, a-f, A-F
      for (Character ch : x.toCharArray()) {
        if (hexdigits.indexOf(ch) == -1) return "Neither";
      }
    }
    return "IPv6";
  }

  /**
   * 检查IP地址合法性
   * @param IP 待检查的IP地址
   * @return 
   */
  public String validIPAddress(String IP) {
    if (IP.chars().filter(ch -> ch == '.').count() == 3) {
      return validateIPv4(IP);
    }
    else if (IP.chars().filter(ch -> ch == ':').count() == 7) {
      return validateIPv6(IP);
    }
    else return "Neither";
  }
  
  /**
   * 判断端口字符串是否能转化为整数。
   * @param strNum 
   * @return 
   */
  public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_ip;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel server_icon;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_ip_val;
    private javax.swing.JTextField tf_port_val;
    // End of variables declaration//GEN-END:variables
}
