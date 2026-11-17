# Kira Defteri

Her ay gelen kiraları aile arasında paylaştıran, telefona uygun basit bir uygulama.

- Her yerin aylık kirası bir kere girilir. Her ay sadece "Yattı" işaretlenir.
- Öncelikli pay (ör. anne) toplamın yüzdesi ya da sabit tutar olabilir. Kalan para diğer kişilere eşit bölünür, kuruşlar atılır.
- Masraf varsa paylaşmadan önce toplamdan düşülür.
- "Aileye yazılacak mesajı kopyala" ile özet WhatsApp'a yapıştırılabilir.

Uygulama: https://mbedilsah-tech.github.io/Kira_Takip/

## Veriler nerede?

Girilen isimler, tutarlar ve işaretler **yalnızca telefonda** saklanır, GitHub'a gitmez. Bu repodaki `index.html` içinde sadece örnek veri vardır.

Telefon değişirse ya da uygulama silinirse veriler kaybolur. Ara sıra **Ayarlar → Yedeği kopyala** ile yedek alın, metni bir yere (ör. kendinize WhatsApp mesajı) kaydedin. **Yedekten yükle** ile geri yüklenir.

Chrome'da açılan sayfa ile APK'nın verileri ayrıdır; ikisi aynı bilgileri paylaşmaz.

## Kurulum

### 1. Sayfayı yayına al (bir kere)

Repo → **Settings → Pages** → Source: **Deploy from a branch**, Branch: **main**, klasör **/ (root)** → **Save**.
Birkaç dakika sonra uygulama yukarıdaki adreste açılır.

### 2a. En kolay yol: ana ekrana ekle

Telefonda Chrome ile adresi açın → sağ üstteki **⋮** → **Ana ekrana ekle** (ya da **Uygulamayı yükle**).
Simgesi olan, tam ekran açılan bir uygulama olur.

### 2b. APK ile kurmak

Android kabuğu `android/` klasöründedir. Bu klasör değiştiğinde ya da **Actions → APK oluştur → Run workflow** ile elle çalıştırıldığında GitHub APK'yı derler ve **Releases** bölümüne `KiraDefteri.apk` olarak koyar.
Telefonda bu dosyayı indirip açın. "Bilinmeyen kaynaklardan yükleme" izni istenirse verin.

## Güncelleme

Uygulamanın kendisi `index.html` dosyasıdır. Bu dosya GitHub'da güncellenince telefondaki uygulama (ana ekran kısayolu da APK da) bir sonraki açılışta yeni sürümü kullanır. GitHub Pages önbelleği yüzünden değişikliğin görünmesi 10 dakika kadar sürebilir.

Yeni APK yalnızca uygulamanın adı, simgesi ya da `android/` içindeki kod değişirse gerekir.

### İmza anahtarı hakkında

Şu an APK, her derlemede değişen geçici bir anahtarla imzalanıyor. Bu yüzden ileride yeni bir APK, eskisinin üstüne kurulamaz. Önce eskisini silmek gerekir ve **silinince telefondaki veriler de gider**. Yeni APK kurmadan önce mutlaka yedek alın.

Bunu önlemek için sabit bir anahtar eklenebilir. Repo → **Settings → Secrets and variables → Actions** altına şu üç secret eklenirse workflow onu kullanır:

| Secret | İçeriği |
| --- | --- |
| `KIRA_KEYSTORE_BASE64` | `keytool` ile üretilmiş `.jks` dosyasının base64 hali |
| `KIRA_KEYSTORE_PASSWORD` | Anahtar deposunun şifresi |
| `KIRA_KEY_ALIAS` | Anahtarın adı (alias) |

Anahtar dosyasını ve şifreyi kaybetmeyin, repoya da koymayın.
