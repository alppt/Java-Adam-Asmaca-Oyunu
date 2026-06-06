/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.adamasmaca;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import java.awt.Font;

/**
 *
 * @author alp__
 */
public class AnaOyunEkrani extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AnaOyunEkrani.class.getName());

    javax.swing.Timer zamanlayici;
    int gecenSaniye = 0;
    String oyunlarDosyaYolu = "C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt";
    //oyun değişkenleri
    String seciliKelime;
    int yanlisSayisi = 0;
    javax.swing.DefaultListModel<String> model = new javax.swing.DefaultListModel<>();
    
    private void kelimeyiHazirla() {
        try {
            File dosya = new File("C:\\P2Oyun\\TXTDosyalar\\kelimeler.txt");
            
            //dosyanın varlığının kontrolünü yaptığım kısım
            if (!dosya.exists()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Dosya bulunamadı! Yol: " + dosya.getAbsolutePath());
                return;
            }
            
            List<String> kelimeler = Files.readAllLines(dosya.toPath(), Charset.defaultCharset());
            
            if (kelimeler.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Dosya boş!");
                return;
            }
            
            Random rnd = new Random();
            seciliKelime = kelimeler.get(rnd.nextInt(kelimeler.size())).trim().toUpperCase();
            
            pnlHarfler.removeAll();
            for (int i = 0; i < seciliKelime.length(); i++) {
                JLabel lblHarf = new JLabel("*");
                lblHarf.setFont(new Font("Segoe UI", Font.BOLD, 24));
                pnlHarfler.add(lblHarf);
            }
            pnlHarfler.revalidate();
            pnlHarfler.repaint();
            // sayaç işlemi
            if (zamanlayici != null) {
                zamanlayici.stop();
            }
            gecenSaniye = 0;
            lblSure.setText("Süre: 0 sn");
            
            zamanlayici = new javax.swing.Timer(1000, e -> {
                gecenSaniye++;
                lblSure.setText("Süre: " + gecenSaniye + " sn");
            });
            zamanlayici.start();
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "HATA: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void resimGuncelle() {
        try {
            // yanlış sayısına göre resim seçme işlemi
            String dosyaYolu = "C:\\P2Oyun\\Resimler\\" + yanlisSayisi + ".jpg";
            java.io.File imgFile = new java.io.File(dosyaYolu);
            
            if(imgFile.exists()) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(dosyaYolu);
                // resmi, lblResim'in boyutuna tam uyacak şekilde ölçeklendirdim.
                java.awt.Image img = icon.getImage().getScaledInstance(lblResim.getWidth(), lblResim.getHeight(), java.awt.Image.SCALE_SMOOTH);
                lblResim.setIcon(new javax.swing.ImageIcon(img));
                lblResim.setText(""); // arkadaki "jLabel1" yazısını sildim.
            }
        } catch (Exception e) {
            System.out.println("Resim yüklenemedi: " + e.getMessage());
        }
    }
    
    private void oyunKaydet(String sonuc) {
        try {
            java.time.LocalDateTime simdi = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String tarihSaat = simdi.format(format);
            
            String kayit = tarihSaat + " | Süre: " + gecenSaniye + " sn | Sonuç: " + sonuc + "\n";
            
            java.io.FileWriter fw = new java.io.FileWriter(oyunlarDosyaYolu, true);
            fw.write(kayit);
            fw.close();
            tablolariGuncelle();
        } catch (Exception e) {
            System.out.println("Oyun kaydedilemedi: " + e.getMessage());
        }
    }
    
    // tabloları text dosyalarını okuyarak dolduran metot
    private void tablolariGuncelle() {
        // skorları doldurduğumuz kısım
        try {
            javax.swing.table.DefaultTableModel skorModel = new javax.swing.table.DefaultTableModel(new String[]{"Oyun Kayıtları"}, 0);
            java.io.File dosyaSkor = new java.io.File("C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt");
            if (dosyaSkor.exists()) {
                java.util.List<String> satirlar = java.nio.file.Files.readAllLines(dosyaSkor.toPath(), java.nio.charset.Charset.defaultCharset());
                for (String satir : satirlar) {
                    skorModel.addRow(new Object[]{satir});
                }
            }
            tblSkorlar.setModel(skorModel);
        } catch (Exception e) {}

        // logları doldurduğumuz kısım
        try {
            javax.swing.table.DefaultTableModel logModel = new javax.swing.table.DefaultTableModel(new String[]{"Sisteme Giriş Logları"}, 0);
            java.io.File dosyaLog = new java.io.File("C:\\P2Oyun\\TXTDosyalar\\log.txt");
            if (dosyaLog.exists()) {
                java.util.List<String> satirlar = java.nio.file.Files.readAllLines(dosyaLog.toPath(), java.nio.charset.Charset.defaultCharset());
                for (String satir : satirlar) {
                    logModel.addRow(new Object[]{satir});
                }
            }
            tblLoglar.setModel(logModel);
        } catch (Exception e) {}
    }

    // temizle butonlarına basılınca şifre sorulur
    private boolean sifreDogruMu() {
        try {
            java.io.File sifreDosyasi = new java.io.File("C:\\P2Oyun\\TXTDosyalar\\sifre.txt");
            java.util.List<String> satirlar = java.nio.file.Files.readAllLines(sifreDosyasi.toPath(), java.nio.charset.Charset.defaultCharset());
            String gercekSifre = satirlar.get(0);
            
            String girilenSifre = javax.swing.JOptionPane.showInputDialog(this, "Silmek için şifrenizi giriniz:");
            
            if (girilenSifre != null && girilenSifre.equals(gercekSifre)) {
                return true;
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Şifre Yanlış!");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Creates new form AnaOyunEkrani
     */
    public AnaOyunEkrani() {
        initComponents();
        // yıldızların görünmesi için yazdığım kod bloğu
        pnlHarfler.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        tablolariGuncelle();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlOyunAlani = new javax.swing.JPanel();
        lblSure = new javax.swing.JLabel();
        pnlHarfler = new javax.swing.JPanel();
        lblResim = new javax.swing.JLabel();
        txtHarfTahmin = new javax.swing.JTextField();
        txtKelimeTahmin = new javax.swing.JTextField();
        btnTahminEt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlSkorlar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSkorlar = new javax.swing.JTable();
        btnSkorTemizle = new javax.swing.JButton();
        pnlLoglar = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoglar = new javax.swing.JTable();
        btnLogTemizle = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemBasla = new javax.swing.JMenuItem();
        jMenuItemYeniden = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setName(""); // NOI18N

        lblSure.setText("Süre: 0 sn");

        javax.swing.GroupLayout pnlHarflerLayout = new javax.swing.GroupLayout(pnlHarfler);
        pnlHarfler.setLayout(pnlHarflerLayout);
        pnlHarflerLayout.setHorizontalGroup(
            pnlHarflerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        pnlHarflerLayout.setVerticalGroup(
            pnlHarflerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        txtHarfTahmin.setColumns(2);
        txtHarfTahmin.addActionListener(this::txtHarfTahminActionPerformed);

        txtKelimeTahmin.setColumns(12);

        btnTahminEt.setText("Tahmin Et");
        btnTahminEt.addActionListener(this::btnTahminEtActionPerformed);

        jLabel1.setText("Harf Tahmini:");

        jLabel2.setText("Kelime Tahmini:");

        javax.swing.GroupLayout pnlOyunAlaniLayout = new javax.swing.GroupLayout(pnlOyunAlani);
        pnlOyunAlani.setLayout(pnlOyunAlaniLayout);
        pnlOyunAlaniLayout.setHorizontalGroup(
            pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblResim, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOyunAlaniLayout.createSequentialGroup()
                                    .addComponent(lblSure)
                                    .addGap(232, 232, 232))
                                .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(30, 30, 30)
                                    .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnTahminEt)
                                        .addComponent(txtHarfTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtKelimeTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(pnlHarfler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlOyunAlaniLayout.setVerticalGroup(
            pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOyunAlaniLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(pnlHarfler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(lblSure)
                .addGap(27, 27, 27)
                .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHarfTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10)
                .addGroup(pnlOyunAlaniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKelimeTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(btnTahminEt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOyunAlaniLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(lblResim, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Oyun Oynama", pnlOyunAlani);

        tblSkorlar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSkorlar);

        btnSkorTemizle.setText("Temizle");
        btnSkorTemizle.addActionListener(this::btnSkorTemizleActionPerformed);

        javax.swing.GroupLayout pnlSkorlarLayout = new javax.swing.GroupLayout(pnlSkorlar);
        pnlSkorlar.setLayout(pnlSkorlarLayout);
        pnlSkorlarLayout.setHorizontalGroup(
            pnlSkorlarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSkorlarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlSkorlarLayout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(btnSkorTemizle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSkorlarLayout.setVerticalGroup(
            pnlSkorlarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSkorlarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnSkorTemizle)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Eski Skorlar", pnlSkorlar);

        tblLoglar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblLoglar);

        btnLogTemizle.setText("Temizle");
        btnLogTemizle.addActionListener(this::btnLogTemizleActionPerformed);

        javax.swing.GroupLayout pnlLoglarLayout = new javax.swing.GroupLayout(pnlLoglar);
        pnlLoglar.setLayout(pnlLoglarLayout);
        pnlLoglarLayout.setHorizontalGroup(
            pnlLoglarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoglarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlLoglarLayout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(btnLogTemizle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoglarLayout.setVerticalGroup(
            pnlLoglarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoglarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogTemizle)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Log Kayıtları", pnlLoglar);

        jMenu1.setText("Oyun");

        jMenuItemBasla.setText("Oyuna Başla");
        jMenuItemBasla.addActionListener(this::jMenuItemBaslaActionPerformed);
        jMenu1.add(jMenuItemBasla);

        jMenuItemYeniden.setText("Yeniden Başlat");
        jMenuItemYeniden.addActionListener(this::jMenuItemYenidenActionPerformed);
        jMenu1.add(jMenuItemYeniden);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTahminEtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTahminEtActionPerformed
        // TODO add your handling code here:
        // harf ve kelime kutularındaki yazıları aldım.
        String kelimeTahmini = txtKelimeTahmin.getText().trim().toUpperCase();
        String harf = txtHarfTahmin.getText().trim().toUpperCase();

        // eğer kelime tahmini kutusu doluysa, yani kelime tahmini yapılıyorsa
        if (!kelimeTahmini.isEmpty()) {
            if (kelimeTahmini.equals(seciliKelime)) {
                // eğer kelime doğru bilinirse tüm yıldızları harfe çevrilir.
                for (int i = 0; i < pnlHarfler.getComponentCount(); i++) {
                    javax.swing.JLabel lbl = (javax.swing.JLabel) pnlHarfler.getComponent(i);
                    lbl.setText(seciliKelime.substring(i, i + 1));
                }
                
                // kazanma durumu
                if (zamanlayici != null) zamanlayici.stop(); // kazanılmışsa sayacı durdururuz
                oyunKaydet("Kazandı"); // ve dosyaya yazarız
                
                javax.swing.JOptionPane.showMessageDialog(this, "Tebrikler! Kelimeyi tek seferde bildiniz!");
                btnTahminEt.setEnabled(false);
            } else {
                // yanlış kelime tahmini yapılmışsa
                yanlisSayisi++;
                resimGuncelle();
                javax.swing.JOptionPane.showMessageDialog(this, "Yanlış Kelime! Adam asılmaya yaklaşıyor...");
                
                if (yanlisSayisi >= 11) {
                    // kaybetme durumu
                    if (zamanlayici != null) zamanlayici.stop(); // Sayacı durduruz
                    oyunKaydet("Kaybetti"); // ve dosyaya yazarız
                    
                    javax.swing.JOptionPane.showMessageDialog(this, "Oyunu Kaybettiniz! Kelime: " + seciliKelime);
                    btnTahminEt.setEnabled(false);
                }
            }
            txtKelimeTahmin.setText(""); // kutuyu temizlediğim kısım
            return; // işlemi bitirdiğim kısım, aşağıdaki harf tahminine inmemesi için
        }

        // eğer harf tahmin kutusu doluysa, yani harf tahmini yapıyorsak
        if (harf.length() != 1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lütfen harf kutusuna 1 harf veya kelime kutusuna kelime girin!");
            return;
        }

        boolean bulundu = false;
        boolean kelimeBittiMi = true; 

        for (int i = 0; i < pnlHarfler.getComponentCount(); i++) {
            javax.swing.JLabel lbl = (javax.swing.JLabel) pnlHarfler.getComponent(i);
            
            if (seciliKelime.substring(i, i + 1).equals(harf)) {
                lbl.setText(harf);
                bulundu = true;
            }
            
            if (lbl.getText().equals("*")) {
                kelimeBittiMi = false;
            }
        }
        
        if (!bulundu) {
            yanlisSayisi++;
            resimGuncelle();
            
            if (yanlisSayisi >= 11) { 
                // kaybetme durumu
                if (zamanlayici != null) zamanlayici.stop(); // sayacı durdururuz
                oyunKaydet("Kaybetti"); // ve dosyaya yazarız
                
                javax.swing.JOptionPane.showMessageDialog(this, "Oyunu Kaybettiniz! Kelime: " + seciliKelime);
                btnTahminEt.setEnabled(false); 
            }
        } 
        else if (kelimeBittiMi) {
            // kazanma durumu
            if (zamanlayici != null) zamanlayici.stop(); // sayacı durdururuz
            oyunKaydet("Kazandı"); // ve dosyaya yazarız
            
            javax.swing.JOptionPane.showMessageDialog(this, "Tebrikler, Oyunu Kazandınız!");
            btnTahminEt.setEnabled(false);
        }
        
        txtHarfTahmin.setText("");
        txtHarfTahmin.requestFocus();
    }//GEN-LAST:event_btnTahminEtActionPerformed

    private void jMenuItemBaslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBaslaActionPerformed
        // TODO add your handling code here:
        kelimeyiHazirla();
    }//GEN-LAST:event_jMenuItemBaslaActionPerformed

    private void jMenuItemYenidenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemYenidenActionPerformed
        // TODO add your handling code here:
        yanlisSayisi = 0; // hata sayısını sıfırladığım kısım
        lblResim.setIcon(null); // adam asmaca resmi kaldırılır yeni bir oyun başlatıldığı için
        lblResim.setText("YENİ OYUN"); // resim kaldırılır ve yerine yeni oyun başlatıldığına dair alanına bilgi yazısı eklenir.
        txtHarfTahmin.setText("");
        txtKelimeTahmin.setText("");
        btnTahminEt.setEnabled(true); // yeni oyun başlatılırsa tahmin butonu tekrar aktif edilmeli.
        
        kelimeyiHazirla(); // oyun yeni bir kelime seçer ve yıldızları tekrar çizer.
    }//GEN-LAST:event_jMenuItemYenidenActionPerformed

    private void txtHarfTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHarfTahminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHarfTahminActionPerformed

    private void btnSkorTemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkorTemizleActionPerformed
        // TODO add your handling code here:
        if (sifreDogruMu()) {
         try {
             // dosyayı içeriği boş olacak şekilde yeniden oluşturduğum kısım
             new java.io.FileWriter("C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt", false).close(); 
             tablolariGuncelle();
             javax.swing.JOptionPane.showMessageDialog(this, "Eski skorlar başarıyla temizlendi.");
         } catch (Exception e) {}
     }
    }//GEN-LAST:event_btnSkorTemizleActionPerformed

    private void btnLogTemizleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogTemizleActionPerformed
        // TODO add your handling code here:
        if (sifreDogruMu()) {
         try {
             new java.io.FileWriter("C:\\P2Oyun\\TXTDosyalar\\log.txt", false).close(); 
             tablolariGuncelle();
             javax.swing.JOptionPane.showMessageDialog(this, "Log kayıtları başarıyla temizlendi.");
         } catch (Exception e) {}
     }
    }//GEN-LAST:event_btnLogTemizleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AnaOyunEkrani().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogTemizle;
    private javax.swing.JButton btnSkorTemizle;
    private javax.swing.JButton btnTahminEt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemBasla;
    private javax.swing.JMenuItem jMenuItemYeniden;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblResim;
    private javax.swing.JLabel lblSure;
    private javax.swing.JPanel pnlHarfler;
    private javax.swing.JPanel pnlLoglar;
    private javax.swing.JPanel pnlOyunAlani;
    private javax.swing.JPanel pnlSkorlar;
    private javax.swing.JTable tblLoglar;
    private javax.swing.JTable tblSkorlar;
    private javax.swing.JTextField txtHarfTahmin;
    private javax.swing.JTextField txtKelimeTahmin;
    // End of variables declaration//GEN-END:variables
}
