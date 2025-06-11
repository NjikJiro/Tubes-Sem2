import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 
import java.util.*; 

abstract class User{
    protected String username, password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

     public String getUsername() {
        return username;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean login(String inputUsername, String inputPassword){
        return username.equals(inputUsername) && password.equals(inputPassword);
    }

    public abstract void viewProfile(); 
}

class Admin extends User{

    public Admin(String username, String password){
        super(username, password);
    }

     @Override
    public void viewProfile() {
        System.out.println("\n[Admin] Username: " + username);
    }

    public void resetPassword(User user, String newPassword) {
        user.password = newPassword;
        System.out.println("Password berhasil direset untuk user: " + user.username);
    }

    public Penghuni tambahPenghuni(String username, String password, String nama, String kamar, String jurusan, String asal, String noTelepon) {
        DataPenghuni newDataPenghuni = new DataPenghuni(nama, kamar, jurusan, asal, noTelepon);
        Penghuni newPenghuni = new Penghuni(username, password, newDataPenghuni);
        return newPenghuni;
    }

    public void tindakLanjutiPengaduan(RiwayatPengaduan riwayat, int index) {
        riwayat.tindakLanjutiPengaduan(index);
    }

    public void selesaikanPengaduan(RiwayatPengaduan riwayat, int index) {
        riwayat.selesaikanPengaduan(index);
    }
}

class Penghuni extends User{
    private DataPenghuni dataPenghuni;

    public Penghuni (String username, String password, DataPenghuni dp){
        super(username, password);
        dataPenghuni = dp;
    }

     @Override
    public void viewProfile() {
        System.out.println("\n[Penghuni] Username: " + username);
    }

    public DataPenghuni getDataPenghuni() {
        return dataPenghuni;
    }

    public void buatPengaduan(String isi, RiwayatPengaduan riwayat) {
        Pengaduan p = new Pengaduan(isi);
        p.tampil();
        riwayat.tambah(p);
        System.out.println("Pengaduan berhasil ditambahkan ke riwayat.");
    }

    public Galeri uploadGallery(String nama, String deskripsi){
        Galeri g = new Galeri(nama, deskripsi);
        g.tampilkan();
        return g;
    }

}

class Penjaga extends User{
    private String nama;

    public Penjaga(String username, String Password, String nama){
        super(username, Password);
        this.nama = nama;
    }

    @Override
     public void viewProfile() {
        System.out.println("\n[Penjaga] Username: " + username);
    }

    public String getNama(){ return nama;}

    public Galeri uploadGallery(String nama, String deskripsi){
        Galeri g = new Galeri(nama, deskripsi);
        g.tampilkan();
        return g;
    }
}

class DataPenghuni {
    private String nama, kamar, jurusan, asal, noTelepon;

    public DataPenghuni(String nama, String kamar, String jurusan, String asal, String noTelepon) {
        this.nama = nama;
        this.kamar = kamar;
        this.jurusan = jurusan;
        this.asal = asal;
        this.noTelepon = noTelepon;
    }

    public String getNama() {
        return nama;
    }

    public String tampilkan() {
        return String.format("Nama : %-30s | Kamar : %-3s | Jurusan : %-5s | Asal : %-10s | No Telepon : %s", nama, kamar, jurusan, asal, noTelepon);
    }


}

class Log{
    private String namaPenghuni;
    private LocalDateTime waktu;
    private String status; // Status: "Masuk" atau "Keluar"

    public Log(String namaPenghuni, String status) {
        this.namaPenghuni = namaPenghuni;
        this.waktu = LocalDateTime.now(); // Waktu saat log dibuat
        this.status = status;
    }

    public void tampilkan() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedWaktu = waktu.format(formatter);
        System.out.println(namaPenghuni + " " + status + " pada " + formattedWaktu);
    }
}

class CCTV{
    private String lokasi;

    public CCTV(String lokasi){
        this.lokasi = lokasi;
    }

    public void tampilkan(){
        System.out.println("Menampilkan CCTV " + lokasi);
    }
}

class Galeri {
    private String namaFile;
    private String deskripsi;

    public Galeri(String namaFile, String deskripsi) {
        this.namaFile = namaFile;
        this.deskripsi = deskripsi;
    }

    public void tampilkan() {
        System.out.println("[Galeri] " + namaFile + ": " + deskripsi);
    }
}

class Pengaduan {
    private String isi;
    private String status = "Baru";

    public Pengaduan(String isi) {
        this.isi = isi;
    }

    public void tindakLanjut() {
        status = "Diproses";
    }

    public void selesai() {
        status = "Selesai";
    }

    public void tampil() {
        System.out.println("[Pengaduan] " + isi + " (" + status + ")");
    }
}

class RiwayatPengaduan {
    private ArrayList<Pengaduan> daftar = new ArrayList<>();

    public void tambah(Pengaduan p) {
        daftar.add(p);
    }

    public void tampilkan() {
        System.out.println("\n--- Riwayat Pengaduan ---");
        if (daftar.isEmpty()) {
            System.out.println("Tidak ada pengaduan.");
            return;
        }
        for (int i = daftar.size() - 1; i >= 0; i--) {
            System.out.print((i + 1) + ". "); 
            daftar.get(i).tampil();
        }
        System.out.println("------------------------");
    }

    public void tindakLanjutiPengaduan(int index) {
        if (index >= 0 && index < daftar.size()) {
            daftar.get(index).tindakLanjut();
        } else {
            System.out.println("Indeks pengaduan tidak valid.");
        }
    }
    
    public void selesaikanPengaduan(int index) {
        if (index >= 0 && index < daftar.size()) {
            daftar.get(index).selesai();
        } else {
            System.out.println("Indeks pengaduan tidak valid.");
        }
    }

    public ArrayList<Pengaduan> getDaftarPengaduan() {
        return daftar;
    }
}

class InventarisAsrama {
    private String namaBarang;
    private int jumlah;
    private String kondisi;

    public InventarisAsrama(String namaBarang, int jumlah, String kondisi) {
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.kondisi = kondisi;
    }

    public void tampilkan() {
        System.out.println(namaBarang + " | Jumlah: " + jumlah + " | Kondisi: " + kondisi);
    }

    public void setKondisi(String kondisi){
        this.kondisi = kondisi;
    }
}

class MiniMap {
    public void tampilkanPeta() {
        System.out.println("  +---------+    +---------+                    +---------+    +---------+");
        System.out.println("  | KAMAR 1 |    | KAMAR11 |                    | KAMAR12 |    | KAMAR22 |");
        System.out.println("  +---------+    +---------+    +-----------+   +---------+    +---------+");
        System.out.println("  +---------+    +---------+    |           |   +---------+    +---------+");
        System.out.println("  | KAMAR 2 |    | KAMAR10 |    |           |   | KAMAR13 |    | KAMAR21 |");
        System.out.println("  +---------+    +---------+    |           |   +---------+    +---------+");
        System.out.println("  +---------+                   |           |                  +---------+");
        System.out.println("  | KAMAR 3 |                   |           |                  | KAMAR20 |");
        System.out.println("  +---------+                   |   LOBI    |                  +---------+");
        System.out.println("  +---------+    +---------+    |           |   +---------+    +---------+");
        System.out.println("  | KAMAR 4 |    | KAMAR 9 |    |           |   | KAMAR14 |    | KAMAR19 |");
        System.out.println("  +---------+    +---------+    |           |   +---------+    +---------+");
        System.out.println("  +---------+    +---------+    |           |   +---------+    +---------+");
        System.out.println("  | KAMAR 5 |    | KAMAR 8 |    |           |   | KAMAR15 |    | KAMAR18 |");
        System.out.println("  +---------+    +---------+    +-----------+   +---------+    +---------+");
        System.out.println("  +---------+    +---------+                    +---------+    +---------+");
        System.out.println("  | KAMAR 6 |    | KAMAR 7 |                    | KAMAR16 |    | KAMAR17 |");
        System.out.println("  +---------+    +---------+                    +---------+    +---------+ ");
        }
}

public class AsramaApp{
    private static Map<String, User> users = new HashMap<>();
    private static ArrayList<Log> daftarLog = new ArrayList<>();
    private static ArrayList<CCTV> daftarCCTV = new ArrayList<>();
    private static ArrayList<Galeri> daftarGaleri = new ArrayList<>();
    private static ArrayList<InventarisAsrama> daftarInventaris = new ArrayList<>();
    private static RiwayatPengaduan riwayatPengaduan = new RiwayatPengaduan();
    private static MiniMap minimap = new MiniMap();

    private static void printMenu(User loggedInUser) {
        System.out.println("\n-------- Menu --------");
        System.out.println("1. Lihat Profil");
        System.out.println("2. Tampilkan Seluruh Penghuni");
        System.out.println("3. Lihat Log Keluar Masuk");
        System.out.println("4. Tampilkan CCTV");
        System.out.println("5. Tampilkan Galeri");
        System.out.println("6. Tampilkan Mini Map");
        if (loggedInUser instanceof Admin) {
            System.out.println("7. Tambah Penghuni Baru");
            System.out.println("8. Lihat Pengaduan");
            System.out.println("9. Kelola Pengaduan");
            System.out.println("10. Tampilkan Inventaris");
        }
        else if (loggedInUser instanceof Penghuni){
            System.out.println("7. Buat Pengaduan");
            System.out.println("8. Lihat Pengaduan");
            System.out.println("9. Upload Galeri");
        }
        else if (loggedInUser instanceof Penjaga){
            System.out.println("7. Lihat Pengaduan");
            System.out.println("8. Upload Galeri");
        }
        System.out.println("0. LogOut");
    }

    private static void tampilkanSemuaDataPenghuni(Map<String, User> users) {
        System.out.println("\n--- Data Lengkap Semua Penghuni ---");
        boolean foundPenghuni = false;
        for (User user : users.values()) {
            if (user instanceof Penghuni) {
                Penghuni penghuni = (Penghuni) user;
                System.out.println(penghuni.getDataPenghuni().tampilkan());
                foundPenghuni = true;
            }
        }
        if (!foundPenghuni) {
            System.out.println("Tidak ada data penghuni yang terdaftar.");
        }
        System.out.println("---------------------------------");
    }

    private static void tampilkanSemuaLog(ArrayList<Log> daftarLog){
        System.out.println("\n--- Log Keluar Masuk ---");
        if (daftarLog.isEmpty()) {
            System.out.println("Tidak ada log keluar masuk.");
        } else {
            for (Log log : daftarLog) {
                log.tampilkan();
            }
        }
        System.out.println("--------------------------------");
    }

    private static void tampilkanSemuaCCTV(ArrayList<CCTV> cctv){
        System.out.println("\n--- Tampilan CCTV ---");
        for (CCTV c : cctv) {
            c.tampilkan();
        }
        System.out.println("--------------------------------");
    }

    private static void tampilkanSemuaGaleri(ArrayList<Galeri> galeri){
         System.out.println("\n--- Galeri ---");
        for (Galeri g : galeri) {
            g.tampilkan();
        }
        System.out.println("--------------------------------");
    }

    private static void tampilkanInventaris(ArrayList<InventarisAsrama> daftarInventaris){
         System.out.println("\n--- Inventaris Asrama ---");
        for (InventarisAsrama i : daftarInventaris) {
            i.tampilkan();
        }
        System.out.println("--------------------------------");
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        DataPenghuni dataYuda = new DataPenghuni("I Gede Arya Lanang Yudarma", "1", "IT", "Gianyar", "081234567");
        Penghuni yuda = new Penghuni("yuda", "bali", dataYuda);
        users.put(yuda.getUsername(), yuda);

        DataPenghuni dataGoklas = new DataPenghuni("Goklas Sipayung", "1", "TT", "Keerom", "0899345431");
        Penghuni goklas = new Penghuni("goklas", "goklas", dataGoklas);
        users.put(goklas.getUsername(), goklas);

        DataPenghuni dataIrfan = new DataPenghuni("Irfan Satrio Yudanto", "3", "IT", "Nabire", "Playboy");
        Penghuni irfan = new Penghuni("madridista", "2323", dataIrfan);
        users.put(irfan.getUsername(), irfan);

        Admin admin = new Admin("admin", "admin");
        users.put(admin.getUsername(), admin);

        Penjaga penjaga = new Penjaga("penjaga", "penjaga", "Ghufron");
        users.put(penjaga.getUsername(), penjaga);

        
        daftarLog.add(new Log(yuda.getDataPenghuni().getNama(), "Masuk"));

        daftarCCTV.add(new CCTV("Teras 1"));
        daftarCCTV.add(new CCTV("Teras 2"));
        daftarCCTV.add(new CCTV("Lorong Utara 1"));
        daftarCCTV.add(new CCTV("Lorong Utara 2"));

        daftarGaleri.add(new Galeri("pic1.jpg", "Foto saat bakar2"));

        riwayatPengaduan.tambah(new Pengaduan("Toilet lantai 2 mampet, butuh perbaikan segera."));
        riwayatPengaduan.tambah(new Pengaduan("Lampu koridor lantai 3 mati, gelap sekali."));
        riwayatPengaduan.tambah(new Pengaduan("Air PDAM sering mati di jam-jam tertentu."));

        daftarInventaris.add(new InventarisAsrama("Kabel Roll", 3, "Baru"));
        
        User loggedInUser = null;
        boolean isRunning = false;

        while(loggedInUser == null) {
            System.out.println("----- Halaman Login -----");
            System.out.print("Username: ");
            String username = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            if (users.containsKey(username)) {
                User potentialUser = users.get(username);
                if (potentialUser.login(username, password)) {
                    loggedInUser = potentialUser;
                    isRunning = true;
                    if (potentialUser instanceof Admin){
                        System.out.println("\nLogin berhasil sebagai " + loggedInUser.getClass().getSimpleName() + "!");
                    }
                    else if (potentialUser instanceof Penghuni) {
                        System.out.println("\nHalo " + ((Penghuni) potentialUser).getDataPenghuni().getNama() + ", Kamu telah berhasil login sebagai penghuni");
                    }
                    else if (potentialUser instanceof Penjaga){
                        System.out.println("\nHalo " + ((Penjaga) potentialUser).getNama() + ", Kamu telah berhasil login sebagai penjaga");
                    }
                    break;
                }
            }
            System.out.println("Login gagal. Username atau password salah. Silakan coba lagi.\n");
        }

        while (isRunning) { 
            printMenu(loggedInUser);
            System.out.print("Pilih menu >> ");
            int choice = -1;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                continue;
            }

            switch (choice) {
                 case 1:
                    loggedInUser.viewProfile();
                    break;
                case 2:
                    tampilkanSemuaDataPenghuni(users);
                    break;
                case 3:
                    tampilkanSemuaLog(daftarLog);
                    break;
                case 4:
                    tampilkanSemuaCCTV(daftarCCTV);
                    break;
                case 5:
                    tampilkanSemuaGaleri(daftarGaleri);
                    break;
                case 6 :
                    minimap.tampilkanPeta();
                    break;
                case 0: // Logout
                    System.out.println("Anda telah logout. Sampai jumpa!");
                    isRunning = false;
                    break;
                default:
                    if (loggedInUser instanceof Admin){
                        Admin currentAdmin = (Admin) loggedInUser;
                        switch (choice) {
                            case 7 :
                                System.out.println("\n--- Tambah Penghuni Baru ---");
                                System.out.print("Username baru: ");
                                String newUname = input.nextLine();
                                if (users.containsKey(newUname)) {
                                    System.out.println("Username ini sudah ada. Silakan pilih username lain.");
                                    break;
                                }
                                System.out.print("Password baru: ");
                                String newPass = input.nextLine();
                                System.out.print("Nama Lengkap: ");
                                String newNama = input.nextLine();
                                System.out.print("Nomor Kamar: ");
                                String newKamar = input.nextLine();
                                System.out.print("Jurusan: ");
                                String newJurusan = input.nextLine();
                                System.out.print("Asal Daerah: ");
                                String newAsal = input.nextLine();
                                System.out.print("Nomor Telepon: ");
                                String newNoTelp = input.nextLine();

                                Penghuni addedPenghuni = currentAdmin.tambahPenghuni(newUname, newPass, newNama, newKamar, newJurusan, newAsal, newNoTelp);
                                users.put(addedPenghuni.getUsername(), addedPenghuni);
                                System.out.println("Penghuni " + newUname + " berhasil ditambahkan ke sistem.");
                            break;
                            case 8 :
                                riwayatPengaduan.tampilkan();
                            break;
                            case 9 :
                                riwayatPengaduan.tampilkan(); // Tampilkan dulu semua pengaduan
                                if (!riwayatPengaduan.getDaftarPengaduan().isEmpty()) {
                                    System.out.print("Masukkan nomor pengaduan yang ingin DITINDAK LANJUTI/SELESAIKAN (1-" + riwayatPengaduan.getDaftarPengaduan().size() + "): ");
                                    int pengaduanIndex = -1;
                                    try {
                                        pengaduanIndex = Integer.parseInt(input.nextLine()) - 1; // Kurangi 1 karena indeks ArrayList dimulai dari 0
                                    } catch (NumberFormatException e) {
                                        System.out.println("Input nomor tidak valid.");
                                        break;
                                    }

                                    if (pengaduanIndex >= 0 && pengaduanIndex < riwayatPengaduan.getDaftarPengaduan().size()) {
                                        System.out.println("Pilih aksi untuk pengaduan nomor " + (pengaduanIndex + 1) + ":");
                                        System.out.println("1. Tindak Lanjuti (status: Diproses)");
                                        System.out.println("2. Selesaikan (status: Selesai)");
                                        System.out.print("Pilih aksi (1/2): ");
                                        int aksiChoice = -1;
                                        try {
                                            aksiChoice = Integer.parseInt(input.nextLine());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Input aksi tidak valid.");
                                            break;
                                        }

                                        if (aksiChoice == 1) {
                                            currentAdmin.tindakLanjutiPengaduan(riwayatPengaduan, pengaduanIndex);
                                        } else if (aksiChoice == 2) {
                                            currentAdmin.selesaikanPengaduan(riwayatPengaduan, pengaduanIndex);
                                        } else {
                                            System.out.println("Pilihan aksi tidak valid.");
                                        }
                                    } else {
                                        System.out.println("Nomor pengaduan tidak ditemukan.");
                                    }
                                }
                            break;
                            
                            case  10:
                                tampilkanInventaris(daftarInventaris);
                            break;
                        }
                    }
                    else if (loggedInUser instanceof Penghuni) {
                        Penghuni currentPenghuni = (Penghuni) loggedInUser;
                        switch (choice) {
                            case 7:
                                System.out.print("Masukkan isi pengaduan Anda: ");
                                String isiPengaduan = input.nextLine();
                                currentPenghuni.buatPengaduan(isiPengaduan, riwayatPengaduan);
                            break;
                            case 8 :
                                riwayatPengaduan.tampilkan();
                            break;

                            case 9 :
                                System.out.println("Upload Galeri");
                                System.out.print("Masukkan nama File : ");
                                String namaFile = input.nextLine();
                                System.out.print("Masukkan Deskripsi : ");
                                String deskripsi = input.nextLine();

                                Galeri addedGaleri = currentPenghuni.uploadGallery(namaFile, deskripsi);
                                daftarGaleri.add(addedGaleri);
                                System.out.println("Berhasil memperbarui Galeri");
                            break;
                        }
                        
                    }
                    else if (loggedInUser instanceof Penjaga) {
                        Penjaga currentPenjaga = (Penjaga) loggedInUser;
                        switch (choice) {
                            case 7:
                                riwayatPengaduan.tampilkan();
                            break;

                            case 8 :
                                System.out.println("Upload Galeri");
                                System.out.print("Masukkan nama File : ");
                                String namaFile = input.nextLine();
                                System.out.print("Masukkan Deskripsi : ");
                                String deskripsi = input.nextLine();

                                Galeri addedGaleri = currentPenjaga.uploadGallery(namaFile, deskripsi);
                                daftarGaleri.add(addedGaleri);
                                System.out.println("Berhasil memperbarui Galeri");
                            break;
                        }
                    }
            }
        }

    }
}