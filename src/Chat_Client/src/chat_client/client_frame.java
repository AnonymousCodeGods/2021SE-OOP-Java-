package chat_client;

import component.ScrollBar;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * 
 * @author 丁家巍
 * @param username 用户名
 * @param address IP地址
 * @param users 用户列表
 * @param port 端口，默认位2222
 * @param isConnectClicked 检查在NewSignUpForm中是否点击了Connect按钮
 * @param isAnnoyClicked 检查在NewSignUpForm中是否点击了Anonymous Login按钮
 * @param sock 用户端的sock
 * @param reader 用户端从服务端读入
 * @param writer 用户端向服务端写入
 */
public class client_frame extends javax.swing.JFrame 
{   
    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false;
    String password;
    boolean isConnectClicked = false;
    boolean isAnonyClicked = false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    
    /**
     * 必要的Getter和Setter函数。
     * @return 
     */

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isIsAnonyClicked() {
        return isAnonyClicked;
    }

    public void setIsAnonyClicked(boolean isAnonyClicked) {
        this.isAnonyClicked = isAnonyClicked;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isIsConnectClicked() {
        return isConnectClicked;
    }

    public void setIsConnectClicked(boolean isConnectClicked) {
        this.isConnectClicked = isConnectClicked;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    //--------------------------//
    /**
     * 根据IncomingReader创建一个用户端线程，等待服务端的数据流。
     */
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //--------------------------//
    /**
     * 向users用户列表加入用户名。
     * @param data 用户名信息
     */
    
    public void userAdd(String data) 
    {
         users.add(data);
    }
    
    //--------------------------//
    /**
     * 从用户列表移除用户名表示用户被移除。
     * @param data 
     */
    
    public void userRemove(String data) 
    {
         ta_chat.append(data + " is now offline.\n");
    }
    
    //--------------------------//
    /**
     * 输出用户列表
     */
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //--------------------------//
    /**
     * 向服务端发送连接信息表示用户连接状态。
     */
    
    public void sendDisconnect() 
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }

    //--------------------------//
    /**
     * 用户点击取消连接后，关闭用户端sock并发送取消连接成功信息。
     */
    
    public void Disconnect() 
    {
        try 
        {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        //tf_username.setEditable(true);

    }
    
    /**
     * client_frame的初始化函数，用于生成ui界面元素并进行一些初始化操作。
     */
    
    public client_frame() 
    {
        initComponents();
        init();
    }
    
    /**
     * 用户在NewSignUpForm点击Connect后记录在isConnectClicked并根据此判断在client_frame
     * 界面需要选择执行什么操作。
     */
    public void start_client_frame() {
        init();
        if(isConnectClicked)
            connect_action();
        else if(isAnonyClicked)
            anony_action();    
    }
    
    /**
     * 点击Connect按钮后的操作，创建用户端socket并创建用户端的读写数据流。
     */
    private void connect_action() {  
        if (isConnected == false) 
        {
            //username = tf_username.getText();
            //tf_username.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true;
                if(username != null){
                    lb_userName_value.setText("Hello!   " + username);
                    lb_userName_value.setFont(new Font("Apple LiGothic", Font.BOLD, 18));
                }
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                //tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }     
    }
    /**
     * 点击Anonymous Login按钮后的操作，创建用户端socket并创建用户端的读写数据流。
     */
    private void anony_action() {
        //tf_username.setText("");
        if(isConnected == true && sock == null)
        {
            JOptionPane.showMessageDialog(this, "Server not started or something wrong with it!");
            System.exit(-1);
        }  
        if (isConnected == false) 
        {
            String anon="anon";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            
            //tf_username.setText(anon);
           //tf_username.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                //tf_username.setEditable(true);
            }
            
            ListenThread();
            
        }
        else if(isConnected == true && sock == null)
        {
            JOptionPane.showMessageDialog(this, "Server not started or something wrong with it!");
            System.exit(-1);
        } 
        else if (isConnected == true && sock != null) 
        {
            ta_chat.append("You are already connected. \n");
        }        
    }
    
    //--------------------------//
    /**
     * 因为可能有多个用户多个线程，所以接入Runnable接口以为了并行方便，利用ListenThread创建
     * 可运行的线程，改函数主要用于接受服务端传输过来的信息并展示在聊天窗口。
     * @see ListenThread
     */
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");

                     if (data[2].equals(chat)) 
                     {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                     } 
                     else if (data[2].equals(connect))
                     {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) { }
        }
    }
    /**
     * 设置图片大小按进行放缩
     * @param srcImg Image类型，图片格式
     * @param w 对图片文档宽进行设置
     * @param h 对图片高度进行设置
     * @return 图片类
     */
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    /**
     * 初始化函数，用于初始化设置需要显示的用户信息、IP地址信息和端口信息。
     */
    private void init() {
        
        lb_ip_dis.setText(address);
        lb_port_dis.setText(Integer.toString(port));
        jLabel3.setText("******");
        if(username != null){
            lb_username_dis.setText(username);
            lb_userName_value.setText("Hello!   " + username);
            lb_userName_value.setFont(new Font("Apple LiGothic", Font.BOLD, 18));
        }
    }

    //--------------------------//
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_address = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        lb_username = new javax.swing.JLabel();
        lb_password = new javax.swing.JLabel();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_anonymous = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        profile = new component.ImageAvatar();
        lb_userName_value = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lb_chat = new javax.swing.JLabel();
        lb_username_dis = new javax.swing.JLabel();
        lb_ip_dis = new javax.swing.JLabel();
        lb_port_dis = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client"); // NOI18N
        setResizable(false);

        lb_address.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_address.setText("Address : ");

        lb_port.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_port.setText("Port :");

        lb_username.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_username.setText("Username :");

        lb_password.setFont(new java.awt.Font("Chalkduster", 0, 13)); // NOI18N
        lb_password.setText("Password : ");

        b_connect.setText("Connect");
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        b_anonymous.setText("Anonymous Login");
        b_anonymous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_anonymousActionPerformed(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        tf_chat.setBorder(null);

        b_send.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send.png"))); // NOI18N
        b_send.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        profile.setBorderSize(1);
        profile.setImage(new javax.swing.ImageIcon(getClass().getResource("/icons/profile.png"))); // NOI18N
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });

        lb_userName_value.setText("Enter Your Name");

        jPanel1.setBackground(new java.awt.Color(50, 199, 249));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        lb_chat.setBackground(new java.awt.Color(0, 0, 0));
        lb_chat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/chat.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_chat)
                .addGap(352, 352, 352))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lb_chat, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        lb_username_dis.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_username_dis.setText("user");

        lb_ip_dis.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_ip_dis.setText("IP");

        lb_port_dis.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        lb_port_dis.setText("Port");

        jLabel3.setFont(new java.awt.Font("Chalkduster", 0, 14)); // NOI18N
        jLabel3.setText("Passwd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_ip_dis)
                            .addComponent(lb_username_dis, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_port_dis)
                            .addComponent(jLabel3))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(b_connect)
                                .addGap(2, 2, 2)
                                .addComponent(b_disconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(b_anonymous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(profile, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lb_userName_value)))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_chat)
                        .addGap(18, 18, 18)
                        .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lb_address)
                                    .addComponent(lb_port)
                                    .addComponent(b_anonymous)
                                    .addComponent(lb_ip_dis)
                                    .addComponent(lb_port_dis))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lb_username)
                                    .addComponent(lb_password)
                                    .addComponent(b_connect)
                                    .addComponent(b_disconnect)
                                    .addComponent(lb_username_dis)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(profile, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_userName_value)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 在聊天窗口端点击连接，用于掉线重新连接。
     * @param evt 点击事件
     */
    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connectActionPerformed
        if (isConnected == false) 
        {
            //username = tf_username.getText();
            //tf_username.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true;
                if(username != null){
                    lb_userName_value.setText("Hello!   " + username);
                    lb_userName_value.setFont(new Font("Apple LiGothic", Font.BOLD, 18));
                }
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                //tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }
    }//GEN-LAST:event_b_connectActionPerformed
    /**
     * 在聊天窗口点击断掉连接，用于用户下线操作。
     * @param evt 点击事件。
     */
    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    /**
     * 在聊天窗口点击匿名登录，用于用户断开连接后再次以匿名身份连接进入。
     * @param evt 点击事件
     */
    private void b_anonymousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_anonymousActionPerformed
        //tf_username.setText("");
        if (isConnected == false) 
        {
            String anon="anon";
            Random generator = new Random(); 
            int i = generator.nextInt(999) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            
            //tf_username.setText(anon);
           //tf_username.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                //tf_username.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }        
    }//GEN-LAST:event_b_anonymousActionPerformed
    /**
     * 用户点击发送后将文本发送给服务端并在聊天窗口显示自己发送的信息。
     * @param evt 点击事件
     */
    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
        /*ButtonModel model = (ButtonModel) evt.getSource();
        if(model.isRollover()) {
            JButton temp1 = new JButton(new ImageIcon(getClass().getClassLoader()
                                          .getResource("icons/send.png")));
            JButton temp2 = b_send;
            b_send = temp1;
        }*/
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }//GEN-LAST:event_b_sendActionPerformed

    /**
     * 用户点击头像修改头像图片
     * @param evt 点击事件
     */
    private void profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseClicked
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Choose Your File");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    // below code selects the file 
        int returnval = filechooser.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION)
        {
        File file = filechooser.getSelectedFile();
        BufferedImage bi;
        try {
            // display the image in a Jlabel
            bi = ImageIO.read(file);
            profile.setImage(new ImageIcon(bi));
        } catch(IOException e) {
           e.printStackTrace(); // todo: implement proper error handeling
        }
        this.pack();
        }
    }//GEN-LAST:event_profileMouseClicked

    /*public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_anonymous;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_chat;
    private javax.swing.JLabel lb_ip_dis;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_port_dis;
    private javax.swing.JLabel lb_userName_value;
    private javax.swing.JLabel lb_username;
    private javax.swing.JLabel lb_username_dis;
    private component.ImageAvatar profile;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_chat;
    // End of variables declaration//GEN-END:variables
}
