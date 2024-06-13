import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class BANKASISTEMI {
    private static JFrame loginFrame;
    private static JPanel loginPanel;
    private static JFrame bankingFrame;
    private static JPanel bankingPanel;
    private static String loggedInUserId;

    public BANKASISTEMI() {
    }

    public static void main(String[] args) {
        createLoginFrame();
    }

    private static void createLoginFrame() {
        loginFrame = new JFrame("Login or Register");
        loginFrame.setSize(900, 500);
        loginFrame.setDefaultCloseOperation(3);
        loginPanel = new JPanel();
        loginFrame.add(loginPanel);
        placeLoginComponents();
        loginFrame.setVisible(true);
    }

    private static void placeLoginComponents() {
        loginPanel.setLayout((LayoutManager)null);
        JLabel jLabel1 = new JLabel();
        jLabel1.setFont(new Font("gothic", 0, 35));
        jLabel1.setText("M E L L A H");
        jLabel1.setForeground(new Color(0, 18, 87));
        jLabel1.setBounds(320, 5, 300, 30);
        JLabel a = new JLabel("\ud83c\udf10");
        a.setFont(new Font("gothic", 0, 340));
        a.setForeground(new Color(0, 18, 87));
        a.setBounds(670, -60, 340, 340);
        loginPanel.add(a);
        JLabel jLabel2 = new JLabel();
        jLabel2.setFont(new Font("gothic", 0, 20));
        jLabel2.setText("BANK");
        jLabel2.setForeground(new Color(0, 18, 87));
        jLabel2.setBounds(400, 40, 200, 30);
        loginPanel.add(jLabel1);
        loginPanel.add(jLabel2);
        JLabel userLabel = new JLabel("Username :");
        userLabel.setBounds(100, 200, 80, 25);
        loginPanel.add(userLabel);
        final JTextField userText = new JTextField(20);
        userText.setBounds(350, 200, 219, 30);
        loginPanel.add(userText);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 260, 80, 25);
        loginPanel.add(passwordLabel);
        final JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(350, 260, 219, 30);
        loginPanel.add(passwordText);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(350, 320, 219, 25);
        loginButton.setBackground(new Color(0, 35, 122));
        loginButton.setForeground(new Color(255, 255, 255));
        loginPanel.add(loginButton);
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(780, 420, 90, 25);
        registerButton.setBackground(new Color(0, 35, 122));
        registerButton.setForeground(new Color(255, 255, 255));
        loginPanel.add(registerButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = new String(passwordText.getPassword());
                if (!user.isEmpty() && !password.isEmpty()) {
                    if (BANKASISTEMI.authenticate(user, password)) {
                        BANKASISTEMI.loggedInUserId = user;
                        BANKASISTEMI.loginFrame.dispose();
                        BANKASISTEMI.createBankingFrame();
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "Invalid username or password");
                    }
                } else {
                    JOptionPane.showMessageDialog((Component)null, "Username or password cannot be empty.");
                }

            }
        });
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = new String(passwordText.getPassword());
                if (!user.isEmpty() && !password.isEmpty()) {
                    if (BANKASISTEMI.register(user, password)) {
                        JOptionPane.showMessageDialog((Component)null, "Registration successful. You can now log in.");
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "Registration failed. Please try again with a different username.");
                    }
                } else {
                    JOptionPane.showMessageDialog((Component)null, "Username or password cannot be empty.");
                }

            }
        });
    }

    private static boolean authenticate(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            boolean isValid = resultSet.next();
            connection.close();
            return isValid;
        } catch (SQLException var6) {
            SQLException ex = var6;
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean register(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStatement.setString(1, username);
            ResultSet checkResultSet = checkStatement.executeQuery();
            if (checkResultSet.next()) {
                connection.close();
                return false;
            } else {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ?, 0)");
                statement.setString(1, username);
                statement.setString(2, password);
                int rowsInserted = statement.executeUpdate();
                connection.close();
                return rowsInserted > 0;
            }
        } catch (SQLException var7) {
            SQLException ex = var7;
            ex.printStackTrace();
            return false;
        }
    }

    private static void createBankingFrame() {
        bankingFrame = new JFrame("Banking Menu");
        bankingFrame.setSize(900, 500);
        bankingFrame.setDefaultCloseOperation(3);
        bankingPanel = new JPanel();
        bankingFrame.add(bankingPanel);
        placeBankingComponents();
        bankingFrame.setVisible(true);
    }

    private static void placeBankingComponents() {
        bankingPanel.setLayout((LayoutManager)null);
        JLabel titleLabel = new JLabel("WELCOME BACK");
        titleLabel.setForeground(new Color(0, 18, 87));
        titleLabel.setFont(new Font("gothic", 0, 30));
        titleLabel.setBounds(350, 7, 400, 30);
        JLabel user = new JLabel(loggedInUserId);
        user.setForeground(new Color(0, 18, 87));
        user.setFont(new Font("gothic", 0, 25));
        user.setBounds(430, 50, 170, 20);
        bankingPanel.add(user);
        JButton balanceButton = new JButton("Balance");
        balanceButton.setBounds(200, 170, 200, 25);
        balanceButton.setBackground(new Color(0, 0, 190));
        balanceButton.setForeground(new Color(255, 255, 255));
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(200, 240, 200, 25);
        depositButton.setBackground(new Color(0, 0, 190));
        depositButton.setForeground(new Color(255, 255, 255));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(500, 240, 200, 25);
        withdrawButton.setBackground(new Color(0, 0, 190));
        withdrawButton.setForeground(new Color(255, 255, 255));
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(500, 170, 200, 25);
        transferButton.setBackground(new Color(0, 0, 190));
        transferButton.setForeground(new Color(255, 255, 255));
        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 420, 80, 25);
        backButton.setBackground(new Color(0, 0, 190));
        backButton.setForeground(new Color(255, 255, 255));
        bankingPanel.add(titleLabel);
        bankingPanel.add(balanceButton);
        bankingPanel.add(depositButton);
        bankingPanel.add(withdrawButton);
        bankingPanel.add(transferButton);
        bankingPanel.add(backButton);
        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                    PreparedStatement balanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                    balanceStatement.setString(1, BANKASISTEMI.loggedInUserId);
                    ResultSet resultSet = balanceStatement.executeQuery();
                    if (resultSet.next()) {
                        BigDecimal balance = resultSet.getBigDecimal("balance");
                        JOptionPane.showMessageDialog((Component)null, "Your balance is: $" + String.valueOf(balance));
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "Failed to retrieve balance. Please try again.");
                    }

                    connection.close();
                } catch (SQLException var6) {
                    SQLException ex = var6;
                    ex.printStackTrace();
                }

            }
        });
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String depositAmount = JOptionPane.showInputDialog("Enter amount to deposit:");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                    PreparedStatement depositStatement = connection.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
                    depositStatement.setBigDecimal(1, new BigDecimal(depositAmount));
                    depositStatement.setString(2, BANKASISTEMI.loggedInUserId);
                    int rowsUpdated = depositStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog((Component)null, "Deposit of $" + depositAmount + " successful.");
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "Deposit failed. Please try again.");
                    }

                    connection.close();
                } catch (SQLException var6) {
                    SQLException ex = var6;
                    ex.printStackTrace();
                }

            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String withdrawalAmountStr = JOptionPane.showInputDialog("Çekilecek miktarı giriniz : ");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                    PreparedStatement balanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                    balanceStatement.setString(1, BANKASISTEMI.loggedInUserId);
                    ResultSet resultSet = balanceStatement.executeQuery();
                    if (resultSet.next()) {
                        BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                        BigDecimal withdrawalAmount = new BigDecimal(withdrawalAmountStr);
                        if (withdrawalAmount.compareTo(currentBalance) > 0) {
                            JOptionPane.showMessageDialog((Component)null, "Yetersiz bakiye. Çekmek istediğiniz tutar mevcut bakiyenizden fazla.");
                        } else {
                            PreparedStatement withdrawalStatement = connection.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
                            withdrawalStatement.setBigDecimal(1, withdrawalAmount);
                            withdrawalStatement.setString(2, BANKASISTEMI.loggedInUserId);
                            int rowsUpdated = withdrawalStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog((Component)null, String.valueOf(withdrawalAmount) + " $ tutarında çekme işlemi başarılı.");
                            } else {
                                JOptionPane.showMessageDialog((Component)null, "Çekme işlemi başarısız. Lütfen tekrar deneyin.");
                            }
                        }
                    }

                    connection.close();
                } catch (SQLException var10) {
                    SQLException ex = var10;
                    ex.printStackTrace();
                }

            }
        });
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String transferAmount = JOptionPane.showInputDialog("Transfer edilecek miktarı giriniz :");
                String recipientUsername = JOptionPane.showInputDialog("Alıcı kullanıcının adını giriniz :");
                if (transferAmount != null && recipientUsername != null && !transferAmount.isEmpty() && !recipientUsername.isEmpty()) {
                    try {
                        BigDecimal transferAmountDecimal = new BigDecimal(transferAmount);
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "username", "password");
                        PreparedStatement checkBalanceStatement = null;
                        PreparedStatement senderStatement = null;
                        PreparedStatement recipientStatement = null;
                        ResultSet rs = null;

                        try {
                            checkBalanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                            checkBalanceStatement.setString(1, BANKASISTEMI.loggedInUserId);
                            rs = checkBalanceStatement.executeQuery();
                            if (rs.next()) {
                                BigDecimal senderBalance = rs.getBigDecimal("balance");
                                if (senderBalance.compareTo(transferAmountDecimal) >= 0) {
                                    connection.setAutoCommit(false);
                                    senderStatement = connection.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
                                    senderStatement.setBigDecimal(1, transferAmountDecimal);
                                    senderStatement.setString(2, BANKASISTEMI.loggedInUserId);
                                    int senderRowsUpdated = senderStatement.executeUpdate();
                                    recipientStatement = connection.prepareStatement("UPDATE users SET balance = balance + ? WHERE username = ?");
                                    recipientStatement.setBigDecimal(1, transferAmountDecimal);
                                    recipientStatement.setString(2, recipientUsername);
                                    int recipientRowsUpdated = recipientStatement.executeUpdate();
                                    if (senderRowsUpdated > 0 && recipientRowsUpdated > 0) {
                                        connection.commit();
                                        JOptionPane.showMessageDialog((Component)null, transferAmount + " $ tutarında " + recipientUsername + " kullanıcısına transfer başarılı.");
                                    } else {
                                        connection.rollback();
                                        JOptionPane.showMessageDialog((Component)null, "Transfer başarısız. Lütfen detayları kontrol edip tekrar deneyin.");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog((Component)null, "Yetersiz bakiye. Lütfen tekrar deneyin.");
                                }
                            } else {
                                JOptionPane.showMessageDialog((Component)null, "Böyle bir kullanıcı bulunamadı.");
                            }
                        } catch (SQLException var41) {
                            SQLException exx = var41;
                            if (connection != null) {
                                try {
                                    connection.rollback();
                                } catch (SQLException var40) {
                                    SQLException e1 = var40;
                                    e1.printStackTrace();
                                }
                            }

                            exx.printStackTrace();
                            JOptionPane.showMessageDialog((Component)null, "Veritabanı hatası: " + exx.getMessage());
                        } finally {
                            SQLException e1x;
                            if (rs != null) {
                                try {
                                    rs.close();
                                } catch (SQLException var39) {
                                    e1x = var39;
                                    e1x.printStackTrace();
                                }
                            }

                            if (checkBalanceStatement != null) {
                                try {
                                    checkBalanceStatement.close();
                                } catch (SQLException var38) {
                                    e1x = var38;
                                    e1x.printStackTrace();
                                }
                            }

                            if (senderStatement != null) {
                                try {
                                    senderStatement.close();
                                } catch (SQLException var37) {
                                    e1x = var37;
                                    e1x.printStackTrace();
                                }
                            }

                            if (recipientStatement != null) {
                                try {
                                    recipientStatement.close();
                                } catch (SQLException var36) {
                                    e1x = var36;
                                    e1x.printStackTrace();
                                }
                            }

                            if (connection != null) {
                                try {
                                    connection.close();
                                } catch (SQLException var35) {
                                    e1x = var35;
                                    e1x.printStackTrace();
                                }
                            }

                        }
                    } catch (NumberFormatException var43) {
                        JOptionPane.showMessageDialog((Component)null, "Geçersiz miktar formatı. Lütfen geçerli bir sayı giriniz.");
                    } catch (SQLException var44) {
                        SQLException ex = var44;
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog((Component)null, "Veritabanı bağlantı hatası: " + ex.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog((Component)null, "Transfer miktarı veya alıcı adı boş olamaz.");
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BANKASISTEMI.bankingFrame.dispose();
                BANKASISTEMI.createLoginFrame();
            }
        });
    }
}
