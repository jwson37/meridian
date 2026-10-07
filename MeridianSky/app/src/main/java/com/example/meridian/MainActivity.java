package com.example.meridian;

import android.app.Activity;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    // name,RA(h),Dec(deg),mag  (J2000)
    static final String[] CAT = {
    "Sirius,6.752,-16.716,-1.46","Canopus,6.399,-52.696,-0.74","Arcturus,14.261,19.182,-0.05","Rigil Kent,14.660,-60.834,-0.01",
    "Vega,18.616,38.784,0.03","Capella,5.278,45.998,0.08","Rigel,5.242,-8.202,0.13","Procyon,7.655,5.225,0.34",
    "Betelgeuse,5.919,7.407,0.42","Achernar,1.629,-57.237,0.46","Hadar,14.064,-60.373,0.61","Altair,19.846,8.868,0.76",
    "Acrux,12.443,-63.099,0.76","Aldebaran,4.599,16.509,0.85","Antares,16.490,-26.432,1.06","Spica,13.420,-11.161,0.97",
    "Pollux,7.755,28.026,1.14","Fomalhaut,22.961,-29.622,1.16","Deneb,20.690,45.280,1.25","Mimosa,12.795,-59.689,1.25",
    "Regulus,10.140,11.967,1.35","Adhara,6.977,-28.972,1.50","Castor,7.577,31.889,1.58","Gacrux,12.519,-57.113,1.64",
    "Shaula,17.560,-37.104,1.62","Bellatrix,5.419,6.350,1.64","Elnath,5.438,28.608,1.65","Alnilam,5.604,-1.202,1.69",
    "Alnitak,5.679,-1.943,1.74","Mintaka,5.533,-0.299,2.23","Saiph,5.796,-9.670,2.06","Meissa,5.585,9.934,3.4",
    "Alioth,12.900,55.960,1.76","Dubhe,11.062,61.751,1.79","Merak,11.031,56.382,2.37","Phecda,11.897,53.695,2.44",
    "Megrez,12.257,57.033,3.31","Mizar,13.399,54.925,2.23","Alkaid,13.792,49.313,1.85","Mirfak,3.405,49.861,1.80",
    "Wezen,7.140,-26.393,1.83","Aludra,7.402,-29.303,2.45","Mirzam,6.378,-17.956,1.98","Sargas,17.622,-42.998,1.87",
    "Kaus Aust,18.403,-34.385,1.85","Kaus Med,18.350,-29.828,2.7","Kaus Bor,18.466,-25.422,2.8","Alnasl,18.097,-30.424,3.0",
    "Phi Sgr,18.761,-26.991,3.2","Nunki,18.921,-26.297,2.05","Tau Sgr,19.116,-27.670,3.3","Ascella,19.043,-29.880,2.6",
    "Alhena,6.629,16.399,1.93","Tejat,6.383,22.514,2.9","Mebsuta,6.732,25.131,3.06","Alphard,9.460,-8.659,1.98",
    "Algieba,10.333,19.842,2.28","Zosma,11.235,20.524,2.56","Denebola,11.818,14.572,2.14","Chertan,11.237,15.430,3.3",
    "Eta Leo,10.122,16.763,3.5","Adhafera,10.278,23.417,3.4","Rasalas,9.879,26.007,3.9","Imai,12.252,-58.749,2.8",
    "Dschubba,16.005,-22.622,2.3","Graffias,16.090,-19.805,2.6","Eps Sco,16.836,-34.293,2.3","Lesath,17.512,-37.296,2.7",
    "Sadr,20.370,40.257,2.23","Albireo,19.512,27.960,3.08","Gienah,20.770,33.970,2.48","Fawaris,19.750,45.131,2.87",
    "Zeta Lyr,18.746,37.605,4.4","Delta Lyr,18.908,36.899,4.2","Sheliak,18.835,33.363,3.5","Sulafat,18.982,32.690,3.25",
    "Tarazed,19.771,10.613,2.7","Alshain,19.922,6.407,3.7","Segin,1.907,63.670,3.38","Ruchbah,1.430,60.235,2.68",
    "Gamma Cas,0.945,60.717,2.47","Schedar,0.675,56.537,2.24","Caph,0.153,59.150,2.28","Alpheratz,0.139,29.091,2.06",
    "Mirach,1.162,35.621,2.05","Polaris,2.530,89.264,1.98","Hamal,2.120,23.462,2.0","Algol,3.136,40.956,2.1",
    "Zeta Tau,5.627,21.143,3.0","Gamma Tau,4.330,15.628,3.65","Ain,4.477,19.180,3.5","Rasalhague,17.582,12.560,2.07",
    "Eltanin,17.943,51.489,2.23","Kochab,14.845,74.156,2.08","Menkent,14.111,-36.370,2.06","Diphda,0.726,-17.987,2.04",
    "Peacock,20.427,-56.735,1.94","Atria,16.811,-69.028,1.92","Miaplacidus,9.220,-69.717,1.68","Menkalinan,5.992,44.948,1.9"};
    static final String LINES =
    "Betelgeuse-Meissa,Meissa-Bellatrix,Betelgeuse-Alnitak,Bellatrix-Mintaka,Mintaka-Alnilam,Alnilam-Alnitak,Alnitak-Saiph,Mintaka-Rigel,"+
    "Sirius-Mirzam,Sirius-Wezen,Wezen-Adhara,Wezen-Aludra,Castor-Pollux,Castor-Mebsuta,Mebsuta-Tejat,Pollux-Alhena,"+
    "Regulus-Eta Leo,Eta Leo-Algieba,Algieba-Adhafera,Adhafera-Rasalas,Regulus-Chertan,Chertan-Denebola,Denebola-Zosma,Zosma-Algieba,Zosma-Chertan,"+
    "Acrux-Gacrux,Mimosa-Imai,Dubhe-Merak,Merak-Phecda,Phecda-Megrez,Megrez-Dubhe,Megrez-Alioth,Alioth-Mizar,Mizar-Alkaid,"+
    "Graffias-Dschubba,Dschubba-Antares,Antares-Eps Sco,Eps Sco-Sargas,Sargas-Shaula,Shaula-Lesath,"+
    "Alnasl-Kaus Aust,Alnasl-Kaus Bor,Kaus Aust-Kaus Med,Kaus Med-Kaus Bor,Kaus Bor-Phi Sgr,Phi Sgr-Nunki,Nunki-Tau Sgr,Tau Sgr-Ascella,Ascella-Kaus Aust,"+
    "Deneb-Sadr,Sadr-Albireo,Fawaris-Sadr,Sadr-Gienah,Vega-Zeta Lyr,Zeta Lyr-Sheliak,Sheliak-Sulafat,Sulafat-Delta Lyr,Delta Lyr-Zeta Lyr,"+
    "Tarazed-Altair,Altair-Alshain,Segin-Ruchbah,Ruchbah-Gamma Cas,Gamma Cas-Schedar,Schedar-Caph,Alpheratz-Mirach,"+
    "Zeta Tau-Aldebaran,Aldebaran-Gamma Tau,Gamma Tau-Ain,Ain-Elnath,Capella-Menkalinan";

    // Precise J2000 (Hipparcos-class): name|RA h m s|Dec d m s|pmRA* mas/yr|pmDec mas/yr
    static final String[] PREC = {
    "Sirius|6 45 8.917|-16 42 58.02|-546.01|-1223.07","Canopus|6 23 57.110|-52 41 44.38|19.93|23.24",
    "Arcturus|14 15 39.672|19 10 56.67|-1093.45|-1999.40","Rigil Kent|14 39 36.495|-60 50 02.37|-3679.25|473.67",
    "Vega|18 36 56.336|38 47 01.29|200.94|286.23","Capella|5 16 41.359|45 59 52.77|75.52|-427.11",
    "Rigel|5 14 32.272|-8 12 05.90|1.31|0.50","Procyon|7 39 18.119|5 13 29.96|-714.59|-1036.80",
    "Betelgeuse|5 55 10.305|7 24 25.43|27.54|11.30","Achernar|1 37 42.845|-57 14 12.31|88.02|-40.08",
    "Altair|19 50 46.999|8 52 05.96|536.23|385.29","Aldebaran|4 35 55.239|16 30 33.49|62.78|-189.36",
    "Antares|16 29 24.460|-26 25 55.21|-12.11|-23.30","Spica|13 25 11.579|-11 09 40.75|-42.35|-30.67",
    "Pollux|7 45 18.950|28 01 34.32|-625.69|-45.95","Fomalhaut|22 57 39.046|-29 37 20.05|328.95|-164.67",
    "Deneb|20 41 25.916|45 16 49.22|1.56|1.55","Regulus|10 08 22.311|11 58 01.95|-249.40|4.91",
    "Castor|7 34 35.873|31 53 17.82|-206.33|-148.18","Polaris|2 31 49.095|89 15 50.79|44.48|-11.85",
    "Acrux|12 26 35.896|-63 05 56.73|-35.83|-14.86","Mimosa|12 47 43.269|-59 41 19.58|-48.24|-44.20",
    "Gacrux|12 31 09.960|-57 06 47.57|28.23|-265.01","Hadar|14 03 49.405|-60 22 22.93|-33.27|-23.16",
    "Bellatrix|5 25 07.863|6 20 58.93|-8.75|-13.28","Elnath|5 26 17.513|28 36 26.83|23.28|-174.22",
    "Alnilam|5 36 12.813|-1 12 06.91|1.49|-1.06","Alnitak|5 40 45.527|-1 56 33.26|3.99|2.54",
    "Mintaka|5 32 00.400|-0 17 56.74|1.67|0.56","Saiph|5 47 45.389|-9 40 10.58|1.55|-1.20",
    "Shaula|17 33 36.520|-37 06 13.76|-8.90|-29.95","Dubhe|11 03 43.672|61 45 03.72|-136.46|-35.25",
    "Alioth|12 54 01.750|55 57 35.36|111.74|-8.99","Alkaid|13 47 32.439|49 18 47.76|-121.23|-15.56",
    "Mizar|13 23 55.541|54 55 31.27|121.23|-22.01","Merak|11 01 50.477|56 22 56.73|81.66|33.74",
    "Phecda|11 53 49.847|53 41 41.14|107.76|11.16","Megrez|12 15 25.560|57 01 57.42|103.56|7.81"};
    static final int MAX = 1200;           // capacity; N = stars currently loaded
    int N = CAT.length;
    static final String BSC_URL = "https://vizier.cds.unistra.fr/viz-bin/asu-tsv?-source=V/50/catalog&-out=HR,Name,RAJ2000,DEJ2000,Vmag,pmRA,pmDE&Vmag=%3C4.2&-sort=Vmag&-out.max=800";
    double[] pmra = new double[MAX], pmdec = new double[MAX]; boolean[] precise = new boolean[MAX], bsc = new boolean[MAX];
    String[] name = new String[MAX]; double[] ra = new double[MAX], dec = new double[MAX], mag = new double[MAX];
    double[] az = new double[MAX], alt = new double[MAX], off = new double[MAX];
    List<int[]> segs = new ArrayList<>();
    double lat = 40, lon = -75; String sel = null;
    EditText etLat, etLon; TextView info; Sky sky; SharedPreferences prefs;
    Handler h = new Handler();
    boolean night = true; int cBg, cTx, cGrid, cMer, cLine, cStar, cLbl, cSel, cBtn;
    TextView tvTime, tvAz, tvAlt, tvName; Button rec, nb; LinearLayout root;
    void theme() {
        if (night) { cBg=Color.BLACK; cTx=Color.rgb(255,40,40); cGrid=Color.rgb(70,0,0); cMer=Color.rgb(255,60,60); cLine=Color.rgb(140,0,0);
            cStar=Color.rgb(255,70,70); cLbl=Color.rgb(200,40,40); cSel=Color.rgb(255,130,130); cBtn=Color.rgb(60,0,0); }
        else { cBg=Color.BLACK; cTx=Color.WHITE; cGrid=Color.rgb(40,40,70); cMer=Color.YELLOW; cLine=Color.rgb(70,110,170);
            cStar=Color.WHITE; cLbl=Color.rgb(255,220,160); cSel=Color.RED; cBtn=Color.rgb(68,68,68); }
        if (root == null) return;
        root.setBackgroundColor(cBg);
        for (TextView t : new TextView[]{tvTime,tvAz,tvAlt,tvName,etLat,etLon,rec,nb}) { t.setTextColor(cTx); t.setHintTextColor(cGrid); }
        rec.setBackgroundColor(cBtn); nb.setBackgroundColor(cBtn); nb.setText(night ? "Day" : "Night"); sky.invalidate();
    }
    static String dms(double deg) {
        boolean neg = deg < 0; long cs = Math.round(Math.abs(deg)*360000.0);
        return String.format(Locale.US, "%s%d° %02d′ %05.2f″", neg?"-":"", cs/360000, (cs/6000)%60, (cs%6000)/100.0);
    }

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        HashMap<String,Integer> idx = new HashMap<>();
        for (int i = 0; i < N; i++) { String[] p = CAT[i].split(",");
            name[i]=p[0]; ra[i]=Double.parseDouble(p[1]); dec[i]=Double.parseDouble(p[2]); mag[i]=Double.parseDouble(p[3]); idx.put(p[0], i); }
        for (String q : PREC) { String[] f = q.split("\\|"); int i = idx.get(f[0]);
            String[] a = f[1].split(" "), d = f[2].split(" ");
            ra[i] = Double.parseDouble(a[0]) + Double.parseDouble(a[1])/60 + Double.parseDouble(a[2])/3600;
            double sg = f[2].startsWith("-") ? -1 : 1;
            dec[i] = sg*(Math.abs(Double.parseDouble(d[0])) + Double.parseDouble(d[1])/60 + Double.parseDouble(d[2])/3600);
            pmra[i] = Double.parseDouble(f[3]); pmdec[i] = Double.parseDouble(f[4]); precise[i] = true; }
        for (String s : LINES.split(",")) { int d = s.indexOf('-'); // names never start with '-'
            Integer a = idx.get(s.substring(0,d)), c = idx.get(s.substring(d+1)); if (a!=null&&c!=null) segs.add(new int[]{a,c}); }
        prefs = getSharedPreferences("p", MODE_PRIVATE);
        lat = prefs.getFloat("lat", 40f); lon = prefs.getFloat("lon", -75f);

        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        LinearLayout row = new LinearLayout(this);
        etLat = field("Lat", lat); etLon = field("Lon", lon);
        rec = new Button(this); rec.setText("Record"); rec.setOnClickListener(v -> record());
        nb = new Button(this); nb.setOnClickListener(v -> { night = !night; prefs.edit().putBoolean("night", night).apply(); theme(); });
        night = prefs.getBoolean("night", true);
        row.addView(etLat, new LinearLayout.LayoutParams(0, -2, 1)); row.addView(etLon, new LinearLayout.LayoutParams(0, -2, 1)); row.addView(rec); row.addView(nb);
        tvTime = big(34); tvAz = big(30); tvAlt = big(30); tvName = big(16);
        sky = new Sky(this);
        root.addView(row); root.addView(tvTime); root.addView(tvName); root.addView(tvAz); root.addView(tvAlt);
        root.addView(sky, new LinearLayout.LayoutParams(-1, 0, 1));
        theme();
        setContentView(root);
        TextWatcher tw = new TextWatcher() {
            public void beforeTextChanged(CharSequence s,int a,int b,int c){} public void onTextChanged(CharSequence s,int a,int b,int c){}
            public void afterTextChanged(Editable e) { try { lat = Double.parseDouble(etLat.getText().toString()); lon = Double.parseDouble(etLon.getText().toString());
                prefs.edit().putFloat("lat",(float)lat).putFloat("lon",(float)lon).apply(); } catch (Exception x) {} } };
        etLat.addTextChangedListener(tw); etLon.addTextChangedListener(tw);
        loadCatalog();
    }
    TextView big(int sp) { TextView t = new TextView(this); t.setTextSize(sp); t.setTypeface(Typeface.MONOSPACE, Typeface.BOLD); t.setPadding(16,0,16,0); return t; }
    EditText field(String hint, double v) { EditText e = new EditText(this); e.setHint(hint); e.setText(String.valueOf(v));
        
        e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED); return e; }

    @Override protected void onResume() { super.onResume(); tick.run(); }
    @Override protected void onPause() { super.onPause(); h.removeCallbacks(tick); }
    Runnable tick = new Runnable() { public void run() { compute(); updateInfo(); sky.invalidate(); h.postDelayed(this, 30); } };

    // ---- Yale Bright Star Catalogue (J2000), downloaded once from VizieR and cached ----
    void loadCatalog() {
        File cache = new File(getFilesDir(), "bsc.tsv");
        if (cache.exists()) { try { applyBsc(readAll(new FileReader(cache))); return; } catch (Exception e) { cache.delete(); } }
        new Thread(() -> {
            try {
                java.net.HttpURLConnection c = (java.net.HttpURLConnection) new java.net.URL(BSC_URL).openConnection();
                c.setConnectTimeout(15000); c.setReadTimeout(30000);
                String t = readAll(new InputStreamReader(c.getInputStream(), "UTF-8"));
                runOnUiThread(() -> { try { int n = applyBsc(t); FileWriter w = new FileWriter(cache); w.write(t); w.close();
                    Toast.makeText(this, "Added " + n + " stars", Toast.LENGTH_LONG).show(); }
                    catch (Exception e) { Toast.makeText(this, "Catalog error: " + e.getMessage(), Toast.LENGTH_LONG).show(); } });
            } catch (Exception e) { runOnUiThread(() -> Toast.makeText(this, "Star download failed (internet needed once): " + e, Toast.LENGTH_LONG).show()); }
        }).start();
    }
    static String readAll(Reader r) throws IOException { BufferedReader b = new BufferedReader(r); StringBuilder sb = new StringBuilder(); String l;
        while ((l = b.readLine()) != null) sb.append(l).append('\n'); b.close(); return sb.toString(); }
    static double num(String s) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0; } }
    static double sex(String s) { String[] p = s.trim().split("\\s+"); double sg = s.trim().startsWith("-") ? -1 : 1;
        return sg*(Math.abs(num(p[0])) + (p.length>1?num(p[1])/60:0) + (p.length>2?num(p[2])/3600:0)); }
    int applyBsc(String t) throws Exception {
        String[] hd = null; int iHR=-1,iN=-1,iRA=-1,iDE=-1,iV=-1,iPA=-1,iPD=-1; List<Object[]> rows = new ArrayList<>();
        for (String ln : t.split("\n")) {
            if (ln.startsWith("#") || ln.trim().isEmpty()) continue;
            String[] f = ln.split("\t", -1);
            if (hd == null) { hd = f; for (int k = 0; k < f.length; k++) { String c = f[k].trim();
                if (c.equals("HR")) iHR=k; else if (c.equals("Name")) iN=k; else if (c.equals("RAJ2000")) iRA=k; else if (c.equals("DEJ2000")) iDE=k;
                else if (c.equals("Vmag")) iV=k; else if (c.equals("pmRA")) iPA=k; else if (c.equals("pmDE")) iPD=k; }
                if (iHR<0||iN<0||iRA<0||iDE<0||iV<0||iPA<0||iPD<0) throw new Exception("unexpected columns"); continue; }
            if (!f[iHR].trim().matches("\\d+") || f[iV].trim().isEmpty()) continue;
            rows.add(new Object[]{f[iHR].trim(), f[iN].trim().replaceAll("\\s+"," "), sex(f[iRA])*15, sex(f[iDE]), num(f[iV]), num(f[iPA])*1000, num(f[iPD])*1000});
        }
        Collections.sort(rows, (a, b) -> Double.compare((double)a[4], (double)b[4]));
        HashSet<String> used = new HashSet<>(); for (int i = 0; i < N; i++) used.add(name[i]);
        int added = 0, nOld = N;
        for (Object[] r : rows) {
            double rd = (double)r[2], dd = (double)r[3]; int m = -1;
            for (int j = 0; j < nOld && m < 0; j++) { double dr = (((ra[j]*15 - rd) + 540) % 360 - 180)*Math.cos(Math.toRadians(dd)), dn = dec[j]-dd;
                if (Math.hypot(dr, dn) < 0.05) m = j; }
            if (m >= 0) { if (!precise[m]) { ra[m]=rd/15; dec[m]=dd; pmra[m]=(double)r[5]; pmdec[m]=(double)r[6]; bsc[m]=true; } continue; }
            if (added >= 250 || N >= MAX) continue;
            String n = ((String) r[1]).isEmpty() ? "HR" + r[0] : (String) r[1]; if (!used.add(n)) { n += " #" + r[0]; used.add(n); }
            name[N]=n; ra[N]=rd/15; dec[N]=dd; mag[N]=(double)r[4]; pmra[N]=(double)r[5]; pmdec[N]=(double)r[6]; bsc[N]=true; N++; added++;
        }
        return added;
    }
    static double R(double deg) { return Math.toRadians(deg); }
    /** Apparent topocentric place: proper motion, IAU76 precession, IAU80 nutation (main terms),
     *  full annual aberration, sidereal time w/ equation of equinoxes, Saemundsson refraction. */
    void compute() {
        double jdUT = System.currentTimeMillis()/86400000.0 + 2440587.5, jdTT = jdUT + 69.184/86400.0;
        double T = (jdTT-2451545.0)/36525.0, yrs = T*100;
        double Om = R(125.04452-1934.136261*T), Ls = R(280.4665+36000.7698*T), Lm = R(218.3165+481267.8813*T);
        double dpsi = R((-17.20*Math.sin(Om)-1.32*Math.sin(2*Ls)-0.23*Math.sin(2*Lm)+0.21*Math.sin(2*Om))/3600.0);
        double deps = R((9.20*Math.cos(Om)+0.57*Math.cos(2*Ls)+0.10*Math.cos(2*Lm)-0.09*Math.cos(2*Om))/3600.0);
        double eps = R((84381.448-46.8150*T-0.00059*T*T+0.001813*T*T*T)/3600.0) + deps, ce = Math.cos(eps), se = Math.sin(eps);
        double d = jdUT-2451545.0, Tu = d/36525.0;
        double gmst = 280.46061837 + 360.98564736629*d + 0.000387933*Tu*Tu - Tu*Tu*Tu/38710000.0;
        double lst = R(gmst + lon) + dpsi*ce;
        double p = R(lat), sp = Math.sin(p), cp = Math.cos(p);
        double as = 1.0/206264.806247;
        double zeta = (2306.2181*T+0.30188*T*T+0.017998*T*T*T)*as, zz = (2306.2181*T+1.09468*T*T+0.018203*T*T*T)*as,
               th = (2004.3109*T-0.42665*T*T-0.041833*T*T*T)*as;
        // aberration geometry
        double M = R(357.52911+35999.05029*T), C = (1.914602-0.004817*T)*Math.sin(M)+0.019993*Math.sin(2*M)+0.000289*Math.sin(3*M);
        double sunL = R(280.46646+36000.76983*T+C), ecc = 0.016708634-0.000042037*T, per = R(102.93735+1.71946*T), kap = 20.49552*as;
        double mer = lat >= 0 ? 180 : 0;
        for (int i = 0; i < N; i++) {
            double a0 = R(ra[i]*15), d0 = R(dec[i]);
            a0 += pmra[i]/1000.0*as/Math.cos(d0)*yrs; d0 += pmdec[i]/1000.0*as*yrs;       // proper motion
            double A = Math.cos(d0)*Math.sin(a0+zeta), B = Math.cos(th)*Math.cos(d0)*Math.cos(a0+zeta)-Math.sin(th)*Math.sin(d0),
                   Cc = Math.sin(th)*Math.cos(d0)*Math.cos(a0+zeta)+Math.cos(th)*Math.sin(d0);
            double a = Math.atan2(A, B) + zz, dd = Math.asin(Cc);                           // precession
            double ta = Math.tan(dd), sa = Math.sin(a), ca = Math.cos(a);
            double da = (ce+se*sa*ta)*dpsi - ca*ta*deps, dn = se*ca*dpsi + sa*deps;        // nutation
            a += da; dd += dn; sa = Math.sin(a); ca = Math.cos(a); double sd = Math.sin(dd), cd = Math.cos(dd);
            for (int k = 0; k < 2; k++) {                                                    // aberration
                double lam = k == 0 ? sunL : per, f = k == 0 ? -kap : ecc*kap;
                a += f*(ca*Math.cos(lam)*ce + sa*Math.sin(lam))/cd;
                dd += f*(Math.cos(lam)*ce*(se/ce*cd - sa*sd) + ca*sd*Math.sin(lam));
            }
            double H = lst - a; sd = Math.sin(dd); cd = Math.cos(dd);
            double h = Math.toDegrees(Math.asin(sd*sp + cd*cp*Math.cos(H)));
            az[i] = (Math.toDegrees(Math.atan2(Math.sin(H), Math.cos(H)*sp - sd/cd*cp)) + 180 + 360) % 360;
            if (h > -1) h += 1.02/Math.tan(R(h+10.3/(h+5.11)))/60.0;                         // refraction, 1010 hPa 10 C
            alt[i] = h; off[i] = ((az[i] - mer + 540) % 360) - 180;
        }
    }
    boolean vis(int i) { return alt[i] >= 12 && alt[i] <= 42 && Math.abs(off[i]) <= 15; }
    int selIdx() { if (sel == null) return -1; for (int i = 0; i < N; i++) if (name[i].equals(sel)) return i; return -1; }
    void updateInfo() {
        long now = System.currentTimeMillis(); Calendar c = Calendar.getInstance(); c.setTimeInMillis(now);
        int cs = (int)((now % 1000) / 10);
        tvTime.setText(String.format(Locale.US, "%02d:%02d:%02d.%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), cs));
        int i = selIdx();
        if (i < 0) { tvName.setText("Tap a star"); tvAz.setText("Az  --"); tvAlt.setText("Alt --"); return; }
        tvName.setText(String.format(Locale.US, "%s  mag %.2f  catalog %s%s", name[i], mag[i], precise[i] ? "±0.1\"" : bsc[i] ? "±1\" (BSC)" : "±1\u2032 approx", vis(i) ? "" : "  (outside window)"));
        tvAz.setText("Az  " + dms(az[i])); tvAlt.setText("Alt " + dms(alt[i]));
    }
    void record() {
        try {
            File f = new File(getExternalFilesDir(null), "star_log.csv"); boolean nw = !f.exists();
            FileWriter w = new FileWriter(f, true);
            if (nw) w.write("utc,lat,lon,star,mag,az_deg,alt_deg\n");
            String ts = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US){{setTimeZone(TimeZone.getTimeZone("UTC"));}}.format(new Date());
            int n = 0, s = selIdx();
            for (int i = 0; i < N; i++) if (s >= 0 ? i == s : vis(i)) {
                w.write(String.format(Locale.US, "%s,%.5f,%.5f,%s,%.2f,%.3f,%.3f\n", ts, lat, lon, name[i], mag[i], az[i], alt[i])); n++; }
            w.close();
            Toast.makeText(this, "Saved " + n + " rows to\n" + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) { Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show(); }
    }

    class Sky extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG); float s = 1, base = 1, cx, cy; double zoom = 1, cO = 0, cA = 27;
        ScaleGestureDetector sg; GestureDetector gd;
        Sky(Context c) {
            super(c);
            sg = new ScaleGestureDetector(c, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override public boolean onScale(ScaleGestureDetector d) {
                    double o = cO + (d.getFocusX()-cx)/s, a = cA - (d.getFocusY()-cy)/s;   // sky point under the fingers
                    zoom = Math.max(1, Math.min(12, zoom*d.getScaleFactor())); s = base*(float)zoom;
                    cO = o - (d.getFocusX()-cx)/s; cA = a + (d.getFocusY()-cy)/s; clamp(); invalidate(); return true; } });
            gd = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onDown(MotionEvent e) { return true; }
                @Override public boolean onScroll(MotionEvent a, MotionEvent b, float dx, float dy) {
                    if (sg.isInProgress()) return false; cO += dx/s; cA -= dy/s; clamp(); invalidate(); return true; }
                @Override public boolean onDoubleTap(MotionEvent e) { zoom = 1; cO = 0; cA = 27; invalidate(); return true; }
                @Override public boolean onSingleTapUp(MotionEvent e) { pick(e.getX(), e.getY()); return true; } });
        }
        void lay() { base = getWidth()/30f; s = base*(float)zoom; cx = getWidth()/2f; cy = getHeight()/2f; }
        void clamp() { double lo = Math.max(0, 15 - 15/zoom), la = Math.max(0, 15 - getHeight()/(2*s));
            cO = Math.max(-lo, Math.min(lo, cO)); cA = Math.max(27-la, Math.min(27+la, cA)); }
        float X(double o) { return cx + (float)(o-cO)*s; }
        float Y(double a) { return cy + (float)(cA-a)*s; }
        @Override protected void onDraw(Canvas c) {
            lay(); clamp(); c.drawColor(cBg); p.setTextSize(base*0.7f); p.setStyle(Paint.Style.FILL);
            int st = zoom < 3 ? 5 : 1; float ts = p.getTextSize();
            p.setStrokeWidth(1);
            for (int a = 12; a <= 42; a++) if (a % st == 0) { p.setColor(cGrid); c.drawLine(X(-15),Y(a),X(15),Y(a),p); c.drawText(a+"°",4,Y(a)-3,p); }
            for (int o = -15; o <= 15; o++) if (o % st == 0 && o != 0) { p.setColor(cGrid); c.drawLine(X(o),Y(42),X(o),Y(12),p); c.drawText((o>0?"+":"")+o+"°",X(o)+3,ts+2,p); }
            p.setColor(cMer); p.setStrokeWidth(3); c.drawLine(X(0),Y(42),X(0),Y(12),p);
            p.setStrokeWidth(1); c.drawText("Meridian", X(0)+6, 2.4f*ts, p);
            c.save(); c.clipRect(X(-15), Y(42), X(15), Y(12));
            p.setColor(cLine); p.setStrokeWidth(2);
            for (int[] g : segs) if (Math.abs(off[g[0]]-off[g[1]]) < 90) c.drawLine(X(off[g[0]]),Y(alt[g[0]]),X(off[g[1]]),Y(alt[g[1]]),p);
            int si = selIdx();
            for (int i = 0; i < N; i++) {
                float x = X(off[i]), y = Y(alt[i]); if (x<-50||x>getWidth()+50||y<-50||y>getHeight()+50) continue;
                float r = Math.max(2.5f, (4.2f - (float)mag[i])*base*0.22f);
                p.setColor(cStar); p.setStyle(Paint.Style.FILL); c.drawCircle(x, y, r, p);
                if (mag[i] < 2.6) { p.setColor(cLbl); c.drawText(name[i], x + r + 4, y - r, p); }
                if (i == si) { p.setColor(cSel); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(3); c.drawCircle(x, y, r + 10, p); p.setStyle(Paint.Style.FILL); }
            }
            c.restore();
            p.setColor(cTx); p.setStyle(Paint.Style.FILL); c.drawText(String.format(Locale.US, "x%.1f", zoom), getWidth()-6*ts, getHeight()-6, p);
        }
        void pick(float px, float py) {
            float best = base*3; int bi = -1;
            for (int i = 0; i < N; i++) { if (!vis(i)) continue; float d = (float)Math.hypot(X(off[i])-px, Y(alt[i])-py); if (d < best) { best = d; bi = i; } }
            if (bi >= 0) sel = name[bi]; updateInfo(); invalidate();
        }
        @Override public boolean onTouchEvent(MotionEvent e) { lay(); sg.onTouchEvent(e); gd.onTouchEvent(e); return true; }
    }
}
