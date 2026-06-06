# 🎮 Adam Asmaca (Hangman) - Java Swing

Bu proje, Java programlama dili ve Swing kütüphanesi kullanılarak geliştirilmiş kapsamlı bir **Adam Asmaca** oyunudur. Nesne Yönelimli Programlama (OOP) prensipleri, dosya okuma/yazma (File I/O) işlemleri ve dinamik GUI bileşenleri kullanılarak tasarlanmıştır.

## 🚀 Projenin Özellikleri

* **Güvenli Giriş Sistemi:** Oyuna giriş yapabilmek için ilk açılışta belirlenen şifre ile giriş yapılması zorunludur. 3 hatalı denemede program kendini güvenli bir şekilde kapatır.
* **Dinamik Oyun Alanı:** `kelimeler.txt` dosyasından rastgele çekilen kelimeler, uzunlukları kadar dinamik `JLabel` (yıldız) oluşturularak ekrana yansıtılır.
* **Çift Tahmin Modu:** Kullanıcı dilerse tek tek harf tahmini yapabilir, dilerse direkt kelimeyi tahmin edebilir.
* **Görsel Geri Bildirim:** Hatalı tahminlerde adam asmaca görselleri (`1.jpg`'den `11.jpg`'ye kadar) dinamik olarak güncellenir ve 11. hatada oyun kaybedilir.
* **Zamanlayıcı (Timer):** Oyun süresi arka planda saniye saniye sayılır.
* **Log ve Skor Sistemi (JTable Entegrasyonu):** * Sisteme yapılan tüm giriş ve şifre denemeleri `log.txt` dosyasına tarih ve saat ile kaydedilir ve "Log Kayıtları" sekmesinde listelenir.
  * Oynanan oyunların sonuçları (süre ve kazanma/kaybetme durumu) `oyunlar.txt` dosyasına kaydedilir ve "Eski Skorlar" sekmesinde listelenir.
* **Şifreli Veri Temizleme:** Skor ve log geçmişini silmek isteyen kullanıcının, sistemi sıfırlamak için yönetici şifresini girmesi gerekir.

## 🛠️ Kullanılan Teknolojiler
* **Dil:** Java (JDK)
* **Arayüz:** Java Swing (JFrame, JPanel, JTabbedPane, JMenuBar, JTable, Timer vb.)
* **Veri Saklama:** Yerel TXT Dosyaları (`java.nio.file`, `FileWriter`)

## 📂 Dosya Yapısı ve Kurulum
Projenin sorunsuz çalışabilmesi için `C:\` ana dizininde aşağıdaki dosya hiyerarşisinin bulunması gerekmektedir:

C:\
 └── P2Oyun\
     ├── Resimler\
     │   ├── 1.jpg ... 11.jpg
     └── TXTDosyalar\
         ├── kelimeler.txt
         ├── log.txt
         ├── oyunlar.txt
         └── sifre.txt
📸 Ekran Görüntüleri
Şifre Giriş Ekranı

<img width="595" height="486" alt="Ekran görüntüsü 2026-06-06 175935" src="https://github.com/user-attachments/assets/295fddec-a999-4326-904f-cbb6f34c9511" />

Ana Oyun Ekranı ve Tahmin Mekanizması

<img width="1027" height="588" alt="Ekran görüntüsü 2026-06-06 180043" src="https://github.com/user-attachments/assets/c8fdf214-b3f2-4461-99af-43c8324ed92b" />

Eski Skorlar ve Log Kayıtları Tablosu

<img width="1029" height="589" alt="Ekran görüntüsü 2026-06-06 180325" src="https://github.com/user-attachments/assets/d81055ef-a7e7-4efc-9289-fa5d50cb1ee6" />
<img width="1029" height="587" alt="Ekran görüntüsü 2026-06-06 180340" src="https://github.com/user-attachments/assets/b9043fd3-1758-49c3-9299-73524811b262" />

