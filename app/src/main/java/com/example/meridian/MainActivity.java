package com.example.meridian;

import android.app.Activity;
import android.content.*;
import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import android.util.TypedValue;
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
    "Sirius|6 45 8.917|-16 42 58.02|-546.01|-1223.07|379.21","Canopus|6 23 57.110|-52 41 44.38|19.93|23.24|10.55",
    "Arcturus|14 15 39.672|19 10 56.67|-1093.45|-1999.40|88.83","Rigil Kent|14 39 36.495|-60 50 02.37|-3679.25|473.67|747.1",
    "Vega|18 36 56.336|38 47 01.29|200.94|286.23|130.23","Capella|5 16 41.359|45 59 52.77|75.52|-427.11|76.2",
    "Rigel|5 14 32.272|-8 12 05.90|1.31|0.50|3.78","Procyon|7 39 18.119|5 13 29.96|-714.59|-1036.80|284.56",
    "Betelgeuse|5 55 10.305|7 24 25.43|27.54|11.30|6.55","Achernar|1 37 42.845|-57 14 12.31|88.02|-40.08|22.68",
    "Altair|19 50 46.999|8 52 05.96|536.23|385.29|194.95","Aldebaran|4 35 55.239|16 30 33.49|62.78|-189.36|48.94",
    "Antares|16 29 24.460|-26 25 55.21|-12.11|-23.30|5.89","Spica|13 25 11.579|-11 09 40.75|-42.35|-30.67|12.44",
    "Pollux|7 45 18.950|28 01 34.32|-625.69|-45.95|96.54","Fomalhaut|22 57 39.046|-29 37 20.05|328.95|-164.67|129.81",
    "Deneb|20 41 25.916|45 16 49.22|1.56|1.55|2.31","Regulus|10 08 22.311|11 58 01.95|-249.40|4.91|41.13",
    "Castor|7 34 35.873|31 53 17.82|-206.33|-148.18|64.12","Polaris|2 31 49.095|89 15 50.79|44.48|-11.85|7.54",
    "Acrux|12 26 35.896|-63 05 56.73|-35.83|-14.86|10.13","Mimosa|12 47 43.269|-59 41 19.58|-48.24|-44.20|9.25",
    "Gacrux|12 31 09.960|-57 06 47.57|28.23|-265.01|36.83","Hadar|14 03 49.405|-60 22 22.93|-33.27|-23.16|8.32",
    "Bellatrix|5 25 07.863|6 20 58.93|-8.75|-13.28|12.92","Elnath|5 26 17.513|28 36 26.83|23.28|-174.22|24.89",
    "Alnilam|5 36 12.813|-1 12 06.91|1.49|-1.06|1.65","Alnitak|5 40 45.527|-1 56 33.26|3.99|2.54|4.43",
    "Mintaka|5 32 00.400|-0 17 56.74|1.67|0.56|3.56","Saiph|5 47 45.389|-9 40 10.58|1.55|-1.20|4.52",
    "Shaula|17 33 36.520|-37 06 13.76|-8.90|-29.95|5.71","Dubhe|11 03 43.672|61 45 03.72|-136.46|-35.25|26.38",
    "Alioth|12 54 01.750|55 57 35.36|111.74|-8.99|40.3","Alkaid|13 47 32.439|49 18 47.76|-121.23|-15.56|31.38",
    "Mizar|13 23 55.541|54 55 31.27|121.23|-22.01|39.36","Merak|11 01 50.477|56 22 56.73|81.66|33.74|41.07",
    "Phecda|11 53 49.847|53 41 41.14|107.76|11.16|38.99","Megrez|12 15 25.560|57 01 57.42|103.56|7.81|40.5"};
    // Constellation stick figures: Cons=chain;chain  (Bayer/Flamsteed ids, consecutive stars joined; X@Con = star of another constellation)
    static final String[] CONS = {
    "And=Alp Del Bet Gam;Bet Mu Nu",
    "Ant=Alp Eps Iot",
    "Aps=Alp Gam Bet;Gam Del1",
    "Aqr=Eps Mu Bet Alp The;Alp Gam Zet Eta;Zet Pi",
    "Aql=Bet Alp Gam;Alp Del Lam;Gam Zet Eps",
    "Ara=Alp Bet Gam Del Eta Alp",
    "Ari=Gam Bet Alp 41",
    "Aur=Alp Bet The Bet@Tau Iot Alp",
    "Boo=Alp Eps Del Bet Gam Rho Alp",
    "Cae=Alp Gam Bet Del",
    "Cam=Bet Alp",
    "Cnc=Alp Del Gam Eta;Del Bet",
    "CVn=Alp2 Bet",
    "CMa=Bet Alp Del Eta;Del Eps",
    "CMi=Bet Alp",
    "Cap=Alp2 The Iot Gam Del Zet Ome Psi Bet Alp2",
    "Car=Alp Ups;Eps Iot;Ups Bet",
    "Cas=Bet Alp Gam Del Eps",
    "Cen=Bet Eps Gam Del;Eps Zet Eta",
    "Cep=Alp Bet Gam Iot Zet Alp",
    "Cet=Alp Gam Del Zet The Eta Bet;Eta Iot",
    "Cha=Alp Gam Bet Del2 Gam",
    "Cir=Alp Bet Gam",
    "Col=Eps Alp Bet Gam;Bet Del",
    "Com=Alp Bet Gam",
    "CrA=The Eta Zet Del Bet Alp Gam Eps",
    "CrB=The Bet Alp Gam Del Eps Iot",
    "Crv=Gam Eps Del Bet Gam",
    "Crt=Alp Bet Gam Del Alp",
    "Cru=Gam Alp;Bet Del",
    "Cyg=Alp Gam Bet1;Del Gam Eps",
    "Del=Alp Bet Del Gam Alp;Del Eps",
    "Dor=Alp Gam;Alp Bet Del Alp;Zet Bet",
    "Dra=Gam Bet Nu Xi Gam;Xi Del Eps Chi Zet Eta The Iot Alp Kap Lam",
    "Equ=Eps Alp Gam Del Alp",
    "Eri=Bet Omi1 Gam Pi Del Eps Eta;The1 Iot Kap Phi Chi Alp",
    "For=Alp Bet Nu",
    "Gem=Alp The Tau Eps Mu Eta;Bet Kap Del Zet Gam",
    "Gru=Gam Alp Bet Eps Zet",
    "Her=Zet Eps Pi Eta Zet;Eta Sig Tau;Eps Del Alp1;Zet Bet Gam",
    "Hor=Alp Del Iot Eta",
    "Hya=Del Sig Eta Eps Zet The Iot Alp Lam Nu Pi Gam",
    "Hyi=Alp Bet Gam",
    "Ind=Alp The Bet",
    "Lac=Bet Alp 5 4 2 1",
    "Leo=Alp Eta Gam Zet Mu Eps;Gam Del Bet The Del",
    "LMi=10 21 46 Bet 21",
    "Lep=Mu Eps Alp Bet Gam;Alp Zet Eta;Zet Del Gam",
    "Lib=Alp2 Bet Gam Sig Alp2",
    "Lup=Alp Bet Del Phi1 Eta Gam Eps Kap Zet Alp",
    "Lyn=Alp 38 31 21",
    "Lyr=Alp Zet1 Bet Gam Del2 Zet1",
    "Men=Alp Gam Eta Bet",
    "Mic=Iot Alp Gam Eps",
    "Mon=Bet Gam;Del Alp;Eps Del",
    "Mus=Alp Bet Del Gam Alp;Alp Eps",
    "Nor=Gam2 Eps Eta Gam2",
    "Oct=Nu Bet Del Nu",
    "Oph=Bet Gam Nu;Bet Alp Kap Lam Del Eps Zet Eta",
    "Ori=Lam Alp Zet Kap;Lam Gam Del Bet;Zet Eps Del",
    "Pav=Alp Del Bet Eps Alp",
    "Peg=Alp Bet Alp@And Gam Alp;Alp The Eps;Bet Mu Lam Iot",
    "Per=Eta Gam Alp Del Eps Zet;Alp Bet Rho",
    "Phe=Alp Bet Gam Del Zet Bet",
    "Pic=Bet Gam Alp",
    "Psc=Gam The Iot Lam Kap Gam;Alp Nu Mu Zet Eps Del Ome",
    "PsA=Alp Eps Del Gam Bet",
    "Pup=Zet Rho Pi Tau Nu",
    "Pyx=Bet Alp Gam",
    "Ret=Alp Bet Del Eps Alp",
    "Sge=Gam Del Bet;Del Alp",
    "Sgr=Gam2 Del Lam Phi Sig Tau Zet Eps Gam2;Del Eps",
    "Sco=Pi Del Bet1;Del Sig Alp Tau;Alp Eps Mu1 Zet1 Eta The Iot1 Kap Lam;Lam Ups",
    "Scl=Del Gam Bet Alp",
    "Sct=Bet Alp Gam Del",
    "Ser=Bet Gam Kap Alp Eps Del Gam",
    "Sex=Gam Alp Bet;Alp Del",
    "Tau=Zet Alp Gam Del1 Eps;Gam Lam",
    "Tel=Alp Zet",
    "Tri=Alp Bet Gam Alp",
    "TrA=Alp Bet Gam Alp",
    "Tuc=Alp Gam Zet Eps Alp",
    "UMa=Alp Bet Gam Del Alp;Del Eps Zet Eta",
    "UMi=Alp Del Eps Zet Eta Gam Bet Zet",
    "Vel=Gam2 Lam Psi Mu Phi Kap Del Gam2",
    "Vir=Bet Eta Gam Del Eps;Gam The Alp;Del Mu",
    "Vol=Eps Del Gam Zet;Bet Del",
    "Vul=Alp 13"};
    static final double[][] NUT = {
        {0,0,0,0,1,-171996,-174.2,92025,8.9},
        {-2,0,0,2,2,-13187,-1.6,5736,-3.1},
        {0,0,0,2,2,-2274,-0.2,977,-0.5},
        {0,0,0,0,2,2062,0.2,-895,0.5},
        {0,1,0,0,0,1426,-3.4,54,-0.1},
        {0,0,1,0,0,712,0.1,-7,0},
        {-2,1,0,2,2,-517,1.2,224,-0.6},
        {0,0,0,2,1,-386,-0.4,200,0},
        {0,0,1,2,2,-301,0,129,-0.1},
        {-2,-1,0,2,2,217,-0.5,-95,0.3},
        {-2,0,1,0,0,-158,0,0,0},
        {-2,0,0,2,1,129,0.1,-70,0},
        {0,0,-1,2,2,123,0,-53,0},
        {2,0,0,0,0,63,0,0,0},
        {0,0,1,0,1,63,0.1,-33,0},
        {2,0,-1,2,2,-59,0,26,0},
        {0,0,-1,0,1,-58,-0.1,32,0},
        {0,0,1,2,1,-51,0,27,0},
        {-2,0,2,0,0,48,0,0,0},
        {0,0,-2,2,1,46,0,-24,0},
        {2,0,0,2,2,-38,0,16,0},
        {0,0,2,2,2,-31,0,13,0},
        {0,0,2,0,0,29,0,0,0},
        {-2,0,1,2,2,29,0,-12,0},
        {0,0,0,2,0,26,0,0,0},
        {-2,0,0,2,0,-22,0,0,0},
        {0,0,-1,2,1,21,0,-10,0},
        {0,2,0,0,0,17,-0.1,0,0},
        {2,0,-1,0,1,16,0,-8,0},
        {-2,2,0,2,2,-16,0.1,7,0},
        {0,1,0,0,1,-15,0,9,0},
        {-2,0,1,0,1,-13,0,7,0},
        {0,-1,0,0,1,-12,0,6,0},
        {0,0,2,-2,0,11,0,0,0},
        {2,0,-1,2,1,-10,0,5,0},
        {2,0,1,2,2,-8,0,3,0},
        {0,1,0,2,2,7,0,0,0},
        {-2,1,1,0,0,-7,0,0,0},
        {0,-1,0,2,2,-7,0,3,0},
        {2,0,0,2,1,-7,0,3,0},
        {2,0,1,0,0,6,0,0,0},
        {-2,0,2,2,2,6,0,-3,0},
        {-2,0,1,2,1,6,0,-3,0},
        {2,0,-2,0,1,-6,0,3,0},
        {2,0,0,0,1,-6,0,3,0},
        {0,-1,1,0,0,5,0,0,0},
        {-2,-1,0,2,1,-5,0,3,0},
        {-2,0,0,0,1,-5,0,3,0},
        {0,0,2,2,1,-5,0,3,0},
        {-2,0,2,0,1,4,0,0,0},
        {-2,1,0,2,1,4,0,0,0},
        {0,0,1,-2,0,4,0,0,0},
        {-1,0,1,0,0,-4,0,0,0},
        {-2,1,0,0,0,-4,0,0,0},
        {1,0,0,0,0,-4,0,0,0},
        {0,0,1,2,0,3,0,0,0},
        {0,0,-2,2,2,-3,0,0,0},
        {-1,-1,1,0,0,-3,0,0,0},
        {0,1,1,0,0,-3,0,0,0},
        {0,-1,1,2,2,-3,0,0,0},
        {2,-1,-1,2,2,-3,0,0,0},
        {0,0,3,2,2,-3,0,0,0},
        {2,-1,0,2,2,-3,0,0,0}};
    /** Nutation in longitude/obliquity (radians): 63 largest IAU 1980 terms. */
    static double[] nutation(double T) {
        double D = R(297.85036 + 445267.111480*T - 0.0019142*T*T + T*T*T/189474.0), M = R(357.52772 + 35999.050340*T - 0.0001603*T*T - T*T*T/300000.0),
               Mp = R(134.96298 + 477198.867398*T + 0.0086972*T*T + T*T*T/56250.0), F = R(93.27191 + 483202.017538*T - 0.0036825*T*T + T*T*T/327270.0),
               Om = R(125.04452 - 1934.136261*T + 0.0020708*T*T + T*T*T/450000.0), sp = 0, se = 0;
        for (double[] t : NUT) { double a = t[0]*D + t[1]*M + t[2]*Mp + t[3]*F + t[4]*Om; sp += (t[5] + t[6]*T)*Math.sin(a); se += (t[7] + t[8]*T)*Math.cos(a); }
        return new double[]{ R(sp*1e-4/3600.0), R(se*1e-4/3600.0) };
    }
    static double[][] rx(double a) { double c = Math.cos(a), s = Math.sin(a); return new double[][]{{1,0,0},{0,c,s},{0,-s,c}}; }
    static double[][] rz(double a) { double c = Math.cos(a), s = Math.sin(a); return new double[][]{{c,s,0},{-s,c,0},{0,0,1}}; }
    static double[][] mul(double[][] A, double[][] B) { double[][] C = new double[3][3]; for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) for (int k = 0; k < 3; k++) C[i][j] += A[i][k]*B[k][j]; return C; }
    static double epsA(double t) { return 84381.406 + t*(-46.836769 + t*(-0.0001831 + t*(0.00200340 + t*(-0.000000576 + t*(-0.0000000434))))); }   // mean obliquity, arcsec (IAU 2006)
    /** IAU 2006 bias-precession (Fukushima-Williams angles) plus nutation: ICRS -> true equator and equinox of date. */
    static double[][] precNut(double t, double dpsi, double deps) {
        double as = 1.0/206264.806247;
        double gam = (-0.052928 + t*(10.556378 + t*(0.4932044 + t*(-0.00031238 + t*(-0.000002788 + t*0.0000000260)))))*as;
        double phi = (84381.412819 + t*(-46.811016 + t*(0.0511268 + t*(0.00053289 + t*(-0.000000440 + t*(-0.0000000176))))))*as;
        double psi = (-0.041775 + t*(5038.481484 + t*(1.5584175 + t*(-0.00018522 + t*(-0.000026452 + t*(-0.0000000148))))))*as;
        double[][] q = rz(gam); q = mul(rx(phi), q); q = mul(rz(-(psi + dpsi)), q); q = mul(rx(-(epsA(t)*as + deps)), q); return q;
    }
    static final int MAX = 5200, TARGET = 5000;   // capacity; N = stars currently loaded
    int N = CAT.length;
    static final String BSC_URL = "https://vizier.cds.unistra.fr/viz-bin/asu-tsv?-source=V/50/catalog&-out=HR,Name,RAJ2000,DEJ2000,Vmag,pmRA,pmDE&Vmag=%3C6.6&-sort=Vmag&-out.max=9300";
    double[] pmra = new double[MAX], pmdec = new double[MAX], plx = new double[MAX], pshift = new double[MAX]; boolean[] precise = new boolean[MAX], bsc = new boolean[MAX], hip = new boolean[MAX];
    String[] name = new String[MAX]; double[] ra = new double[MAX], dec = new double[MAX], mag = new double[MAX];
    double[] az = new double[MAX], alt = new double[MAX], off = new double[MAX];
    List<int[]> segs = new ArrayList<>();
    double lat = 40, lon = -75, elev = 0, tempF = 50, pressHpa = Double.NaN, xi = 0, eta = 0; EditText etElev, etTemp, etPress, etXi, etEta; String sel = null;
    EditText etLat, etLon; TextView info; Sky sky; SharedPreferences prefs;
    Handler h = new Handler();
    boolean night = true; int cBg, cTx, cGrid, cMer, cLine, cStar, cLbl, cSel, cBtn, cAltLn, cAltTx; Button lockB, syncB; boolean locked;
    TextView tvTime, tvAz, tvAlt, tvZen, tvName, tvMer, tvHint, tvGeo1, tvGeo2, tvGeo3, tvDov1, tvDov2, tvDov3, tvPlx; Button dovB; String dovSrc = "manual"; boolean settingDov; LinearLayout page2; Pager pg; Button clrB; int curPage; List<TextView> extra = new ArrayList<>(); double azRef = Double.NaN; int cFalse; Button rec, nb; LinearLayout root;
    void theme() {
        if (night) { cBg=Color.BLACK; cTx=Color.rgb(255,40,40); cGrid=Color.rgb(70,0,0); cMer=Color.rgb(255,60,60); cLine=Color.rgb(140,0,0);
            cStar=Color.rgb(255,70,70); cLbl=Color.rgb(200,40,40); cSel=Color.rgb(255,130,130); cBtn=Color.rgb(60,0,0); cAltLn=Color.rgb(170,30,30); cAltTx=Color.rgb(255,100,100); cFalse=Color.rgb(255,175,175); }
        else { cBg=Color.BLACK; cTx=Color.WHITE; cGrid=Color.rgb(40,40,70); cMer=Color.YELLOW; cLine=Color.rgb(70,110,170);
            cStar=Color.WHITE; cLbl=Color.rgb(255,220,160); cSel=Color.RED; cBtn=Color.rgb(68,68,68); cAltLn=Color.rgb(110,110,170); cAltTx=Color.rgb(200,200,255); cFalse=Color.rgb(0,220,255); }
        if (root == null) return;
        root.setBackgroundColor(cBg); page2.setBackgroundColor(cBg); pg.setBackgroundColor(cBg); for (TextView x : extra) x.setTextColor(cTx);
        for (TextView t : new TextView[]{tvTime,tvAz,tvAlt,tvZen,tvHint,tvGeo1,tvGeo2,tvGeo3,tvDov1,tvDov2,tvDov3,tvPlx,tvEop,etDut,etXp,etYp,dovB,clrB,tvName,tvMer,etLat,etLon,etElev,etTemp,etPress,rec,nb,lockB,syncB}) { t.setTextColor(cTx); t.setHintTextColor(cGrid); }
        rec.setBackgroundColor(cBtn); nb.setBackgroundColor(cBtn); lockB.setBackgroundColor(cBtn); syncB.setBackgroundColor(cBtn); clrB.setBackgroundColor(cBtn); dovB.setBackgroundColor(cBtn); nb.setText(night ? "Day" : "Night"); sky.invalidate();
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
            pmra[i] = Double.parseDouble(f[3]); pmdec[i] = Double.parseDouble(f[4]); plx[i] = f.length > 5 ? Double.parseDouble(f[5])/1000.0 : 0; precise[i] = true; }
        for (String s : LINES.split(",")) { int d = s.indexOf('-'); // names never start with '-'
            Integer a = idx.get(s.substring(0,d)), c = idx.get(s.substring(d+1)); if (a!=null&&c!=null) segs.add(new int[]{a,c}); }
        prefs = getSharedPreferences("p", MODE_PRIVATE);
        lat = Double.parseDouble(prefs.getString("latS", "40")); lon = Double.parseDouble(prefs.getString("lonS", "-75"));
        elev = Double.parseDouble(prefs.getString("elevS", "0")); xi = Double.parseDouble(prefs.getString("xiS", "0")); dovSrc = prefs.getString("dovSrcS", "manual"); eta = Double.parseDouble(prefs.getString("etaS", "0")); tempF = Double.parseDouble(prefs.getString("tempS", "50"));
        String psv = prefs.getString("pressInS", ""); pressHpa = psv.isEmpty() ? Double.NaN : Double.parseDouble(psv)*33.8639;   // field is inHg

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        night = prefs.getBoolean("night", true);
        etLat = field("Lat", lat); etLon = field("Lon", lon);
        etElev = field("Elev", elev); etTemp = field("Temp", tempF); etPress = field("inHg auto", 0); etPress.setText(psv); etXi = field("xi", xi); etEta = field("eta", eta);
        etDut = field("DUT1", 0); etXp = field("xp", 0); etYp = field("yp", 0); for (EditText ed2 : new EditText[]{etDut, etXp, etYp}) { ed2.setText(""); ed2.setTextSize(20); }
        for (EditText ed : new EditText[]{etLat, etLon, etElev, etTemp, etPress, etXi, etEta}) ed.setTextSize(20);
        rec = btn("Record"); rec.setOnClickListener(v -> record());
        lockB = btn("Lock"); lockB.setOnClickListener(v -> setLocked(!locked));
        syncB = btn("Sync"); syncB.setOnClickListener(v -> syncTime());
        nb = btn("Day"); nb.setOnClickListener(v -> { night = !night; prefs.edit().putBoolean("night", night).apply(); theme(); });
        clrB = btn("Clear zero"); clrB.setOnClickListener(v -> { azRef = Double.NaN; sky.cO = 0; sky.invalidate(); Toast.makeText(this, "Azimuth zero cleared (true north)", Toast.LENGTH_SHORT).show(); });
        dovB = btn("DoV from NGS (auto \u03be/\u03b7)"); dovB.setOnClickListener(v -> fetchDov(true));
        tvTime = big(28); tvAz = big(30); tvAlt = big(30); tvZen = big(30); tvName = big(16); tvMer = big(26); tvHint = big(11); tvGeo1 = big(16); tvGeo2 = big(16); tvGeo3 = big(16); tvDov1 = big(16); tvDov2 = big(16); tvDov3 = big(13); tvEop = big(13); for (int k = 0; k < tvInfo.length; k++) { tvInfo[k] = big(13); extra.add(tvInfo[k]); } tvPlx = big(16); tvDov1.setPadding(14, 20, 14, 0); tvGeo1.setPadding(14, 28, 14, 0);
        tvHint.setText("tap here = Record     swipe left = inputs >");
        sky = new Sky(this);
        // screen 1: readouts + chart (tap anywhere on the readouts = Record)
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        LinearLayout top = new LinearLayout(this); top.setOrientation(LinearLayout.VERTICAL); top.setClickable(true); top.setOnClickListener(v -> record());
        top.addView(tvTime); top.addView(tvName); top.addView(tvAz); top.addView(tvZen); top.addView(tvMer); top.addView(tvHint);
        root.addView(top); root.addView(sky, new LinearLayout.LayoutParams(-1, 0, 1));
        // screen 2: inputs + buttons
        page2 = new LinearLayout(this); page2.setOrientation(LinearLayout.VERTICAL); page2.setPadding(12, 40, 12, 12);
        page2.addView(pair(etLat, lbl("\u00b0 lat"))); page2.addView(pair(etLon, lbl("\u00b0 lon"))); page2.addView(pair(etElev, lbl("m")));
        page2.addView(pair(etTemp, lbl("\u00b0F"))); page2.addView(pair(etPress, lbl("inHg")));
        page2.addView(pair(etXi, lbl("\u03be \u2033"))); page2.addView(pair(etEta, lbl("\u03b7 \u2033")));
        page2.addView(pair(etDut, lbl("s"))); page2.addView(pair(etXp, lbl("xp \u2033"))); page2.addView(pair(etYp, lbl("yp \u2033")));
        LinearLayout b1 = new LinearLayout(this), b2 = new LinearLayout(this);
        b1.addView(lockB, new LinearLayout.LayoutParams(0, -2, 1)); b1.addView(syncB, new LinearLayout.LayoutParams(0, -2, 1.5f)); b1.addView(nb, new LinearLayout.LayoutParams(0, -2, 1));
        b2.addView(rec, new LinearLayout.LayoutParams(0, -2, 1)); b2.addView(clrB, new LinearLayout.LayoutParams(0, -2, 1));
        page2.addView(b1); page2.addView(b2); page2.addView(dovB); page2.addView(tvGeo1); page2.addView(tvGeo3); page2.addView(tvGeo2); page2.addView(tvDov1); page2.addView(tvDov2); page2.addView(tvDov3); page2.addView(tvPlx); page2.addView(tvEop); for (TextView ti : tvInfo) page2.addView(ti);
        TextView back = lbl("< swipe right to return"); back.setTextSize(13); page2.addView(back);
        int W = getResources().getDisplayMetrics().widthPixels;
        LinearLayout holder = new LinearLayout(this);
        holder.addView(root, new LinearLayout.LayoutParams(W, -1)); ScrollView sv = new ScrollView(this); sv.setFillViewport(true); sv.addView(page2); holder.addView(sv, new LinearLayout.LayoutParams(W, -1));
        pg = new Pager(this); pg.setHorizontalScrollBarEnabled(false); pg.addView(holder, new FrameLayout.LayoutParams(-2, -1));
        pg.setOnTouchListener((v, ev) -> { int act = ev.getAction();
            if (act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_CANCEL) { pg.post(() -> { int w = pg.getWidth(); double pos = pg.getScrollX() / (double) w;
                curPage = curPage == 0 ? (pos > 0.25 ? 1 : 0) : (pos < 0.75 ? 0 : 1); pg.smoothScrollTo(curPage * w, 0); }); }
            return false; });
        theme();
        setContentView(pg);
        TextWatcher tw = new TextWatcher() {
            public void beforeTextChanged(CharSequence q,int a,int b,int c){} public void onTextChanged(CharSequence q,int a,int b,int c){}
            public void afterTextChanged(Editable e) { try { double la = Double.parseDouble(etLat.getText().toString()), lo = Double.parseDouble(etLon.getText().toString());
                if (la >= -90 && la <= 90 && lo >= -180 && lo <= 180) { lat = la; lon = lo;
                    prefs.edit().putString("latS", String.valueOf(la)).putString("lonS", String.valueOf(lo)).apply(); } } catch (Exception x) {} } };
        TextView.OnEditorActionListener done = (v, a, ev) -> { setLocked(true); return true; };
        etLat.setOnEditorActionListener(done); etLon.setOnEditorActionListener(done); etElev.setOnEditorActionListener(done); etTemp.setOnEditorActionListener(done); etPress.setOnEditorActionListener(done); etXi.setOnEditorActionListener(done); etEta.setOnEditorActionListener(done); etDut.setOnEditorActionListener(done); etXp.setOnEditorActionListener(done); etYp.setOnEditorActionListener(done);
        etLat.addTextChangedListener(tw); etLon.addTextChangedListener(tw);
        TextWatcher tw2 = new TextWatcher() {
            public void beforeTextChanged(CharSequence q,int a,int b,int c){} public void onTextChanged(CharSequence q,int a,int b,int c){}
            public void afterTextChanged(Editable e) { try { double el = Double.parseDouble(etElev.getText().toString()), tf = Double.parseDouble(etTemp.getText().toString()), xv = Double.parseDouble(etXi.getText().toString()), ev = Double.parseDouble(etEta.getText().toString());
                String ps = etPress.getText().toString().trim(); double pr = ps.isEmpty() ? Double.NaN : Double.parseDouble(ps)*33.8639;
                if (el >= -500 && el <= 9000 && tf >= -100 && tf <= 150 && Math.abs(xv) <= 120 && Math.abs(ev) <= 120 && (Double.isNaN(pr) || (pr >= 300 && pr <= 1100))) { if (!settingDov && (xv != xi || ev != eta)) dovSrc = "manual";
                    elev = el; tempF = tf; pressHpa = pr; xi = xv; eta = ev;
                    prefs.edit().putString("elevS", String.valueOf(el)).putString("tempS", String.valueOf(tf)).putString("pressInS", ps).putString("dovSrcS", dovSrc).putString("xiS", String.valueOf(xv)).putString("etaS", String.valueOf(ev)).apply(); } } catch (Exception x) {} } };
        TextWatcher tw3 = new TextWatcher() {            // blank = automatic IERS value, typed = manual override
            public void beforeTextChanged(CharSequence q,int a,int b,int c){} public void onTextChanged(CharSequence q,int a,int b,int c){}
            public void afterTextChanged(Editable e) { manDut = pn(etDut); manXp = pn(etXp); manYp = pn(etYp); } };
        etDut.addTextChangedListener(tw3); etXp.addTextChangedListener(tw3); etYp.addTextChangedListener(tw3);
        etElev.addTextChangedListener(tw2); etTemp.addTextChangedListener(tw2); etPress.addTextChangedListener(tw2); etXi.addTextChangedListener(tw2); etEta.addTextChangedListener(tw2);
        setLocked(prefs.getBoolean("locked", false));
        loadEop();
        loadCatalog();
    }
    Button btn(String t) { Button b = new Button(this); b.setText(t); b.setAllCaps(false); b.setTextSize(15); b.setMinHeight(0); b.setMinimumHeight(0); return b; }
    void setLocked(boolean b) {
        locked = b; prefs.edit().putBoolean("locked", b).apply(); if (b) autoDov();
        etLat.setEnabled(!b); etLon.setEnabled(!b); etElev.setEnabled(!b); etTemp.setEnabled(!b); etPress.setEnabled(!b); etXi.setEnabled(!b); etEta.setEnabled(!b); etDut.setEnabled(!b); etXp.setEnabled(!b); etYp.setEnabled(!b); lockB.setText(b ? "Unlock" : "Lock");
        if (b) { View f = getCurrentFocus(); if (f != null) { f.clearFocus();
            ((android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(f.getWindowToken(), 0); } }
        goFull();
    }
    void goFull() { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION); }
    @Override public void onWindowFocusChanged(boolean f) { super.onWindowFocusChanged(f); if (f) goFull(); }
    void fit(TextView t, float maxSp) {          // shrink text so it always stays on one line
        int w = t.getWidth() - t.getPaddingLeft() - t.getPaddingRight(); if (w <= 0) return;
        float px = maxSp * getResources().getDisplayMetrics().scaledDensity;
        Paint pt = new Paint(t.getPaint()); pt.setTextSize(px); float m = pt.measureText(t.getText().toString());
        if (m > w) px *= w / m;
        if (Math.abs(t.getTextSize() - px) > 0.5f) t.setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }
    /** Length of 1 degree of latitude and Earth's surface rotation speed at the entered latitude/elevation (WGS84). */
    void updateGeo() {
        double phi = R(lat), s2 = Math.pow(Math.sin(phi), 2), e2 = 0.00669437999014, a = 6378137.0, w = Math.sqrt(1 - e2*s2);
        double M = a*(1 - e2)/(w*w*w), Nr = a/w;                         // meridional and prime-vertical radii of curvature
        double ft = (M + elev)*Math.PI/180.0/0.3048;                      // feet per degree of latitude
        double v = 7.2921150e-5*(Nr + elev)*Math.cos(phi)/0.3048;         // ft/s from Earth's rotation (sidereal rate)
        tvGeo1.setText(String.format(Locale.US, "1\u00b0 lat = %,.0f ft = %.3f mi", ft, ft/5280.0));
        double ftLon = (Nr + elev)*Math.cos(phi)*Math.PI/180.0/0.3048;     // feet per degree of longitude at this latitude
        tvGeo3.setText(String.format(Locale.US, "1\u00b0 lon = %,.0f ft = %.3f mi", ftLon, ftLon/5280.0));
        tvGeo2.setText(String.format(Locale.US, "Earth spin: %.1f ft/s = %.1f mph", v, v*3600.0/5280.0));
        fit(tvGeo1, 16); fit(tvGeo3, 16); fit(tvGeo2, 16);
        double latA = lat + xi/3600.0, lonA = lon + eta/3600.0/Math.cos(phi);       // astronomic position implied by the deflection of the vertical
        tvDov1.setText(String.format(Locale.US, "DoV \u03be=%+.1f\u2033 \u03b7=%+.1f\u2033 tilt %.1f\u2033", xi, eta, Math.hypot(xi, eta)));
        tvDov2.setText(String.format(Locale.US, "Astro lat %.6f lon %.6f", latA, lonA));
        int psel = selIdx();
        tvPlx.setText(psel < 0 ? "Parallax: select a star" : plx[psel] <= 0 ? "Parallax: n/a " + name[psel] : String.format(Locale.US, "Plx %s %.1f mas, now %.3f\u2033", name[psel], plx[psel]*1000, pshift[psel]));
        tvDov3.setText("DoV source: " + dovSrc);
        etDut.setHint(String.format(Locale.US, "%+.4f auto", autoDut)); etXp.setHint(String.format(Locale.US, "%+.4f auto", autoXp)); etYp.setHint(String.format(Locale.US, "%+.4f auto", autoYp));
        tvEop.setText(eopOk ? String.format(Locale.US, "EOP IERS: UT1-UTC %+.4f s  pole %+.3f %+.3f", curDut, curXp, curYp) : "EOP: none loaded (UT1-UTC=0)"); fit(tvEop, 13);
        double[] qn = ntpS;
        tvInfo[0].setText(qn != null ? String.format(Locale.US, "Clock NTP \u00b1%.0fms jitter %.1fms n=%d age %.0fs", qn[2], qn[3], (int) qn[4], (SystemClock.elapsedRealtimeNanos() - qn[1])/1e9) : "Clock: phone clock (no NTP sync yet)");
        tvInfo[1].setText(String.format(Locale.US, "Refraction %.1f hPa (%s) %.1f\u00b0C scale %.3f", usedPressure(), pressSrc(), (tempF - 32)*5.0/9.0, rfUsed));
        tvInfo[2].setText(psel < 0 ? "Star refraction / diurnal aberr: select a star" : String.format(Locale.US, "%s refr %+.1f\u2033 diurnal %.2f\u2033", name[psel], refr[psel], dab[psel]));
        tvInfo[3].setText(psel < 0 ? "Catalog: select a star" : String.format(Locale.US, "Cat %s  pm %+.1f %+.1f mas/yr", catQ(psel), pmra[psel], pmdec[psel]));
        tvInfo[4].setText(String.format(Locale.US, "Nutation \u0394\u03c8 %+.3f\u2033 \u0394\u03b5 %+.3f\u2033", dpsiAs, depsAs));
        { int gh = (int) gastH; int gm = (int) ((gastH - gh)*60); double gs = ((gastH - gh)*60 - gm)*60; tvInfo[5].setText(String.format(Locale.US, "GAST %02d:%02d:%05.2f  astro lat %.5f", gh, gm, gs, latAUsed)); }
        tvInfo[6].setText("Model: IAU2006 prec, 63-term nut, plx, aberr, DUT1, pole");
        for (TextView ti : tvInfo) fit(ti, 13); fit(tvDov1, 16); fit(tvDov2, 16); fit(tvDov3, 13); fit(tvPlx, 16);
    }
    // ---- Deflection of the vertical from the NGS GEOID18 geoid-height API (DEFLEC18 is derived from GEOID18 but has no API) ----
    double geoidH(double la, double lo) throws Exception {
        String t = fetch(String.format(Locale.US, "https://geodesy.noaa.gov/api/geoid/ght?lat=%.8f&lon=%.8f&model=14", la, lo));
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("\"geoidHeight\"\\s*:\\s*(-?[0-9.]+)").matcher(t);
        if (!m.find()) throw new Exception("no geoid height"); double h = Double.parseDouble(m.group(1)); if (h < -900) throw new Exception("outside model"); return h; }
    void autoDov() {                                   // when the position is locked and has moved >~1 km since the last lookup (USA / Puerto Rico area only)
        try { String a = prefs.getString("dovLatS", null), o = prefs.getString("dovLonS", null);
            boolean moved = a == null || o == null || Math.abs(Double.parseDouble(a) - lat) > 0.01 || Math.abs(Double.parseDouble(o) - lon) > 0.01;
            if (moved && lat > 15 && lat < 59 && lon < -59 && lon > -131) fetchDov(false); } catch (Exception e) {} }
    void fetchDov(boolean manual) {
        final double la = lat, lo = lon;
        new Thread(() -> {
            try {
                double d = 1.0/60.0, phi = R(la), e2 = 0.00669437999014, a = 6378137.0, w = Math.sqrt(1 - e2*Math.pow(Math.sin(phi), 2)), M = a*(1 - e2)/(w*w*w), Nr = a/w, dphi = R(d);
                double nN = geoidH(la + d, lo), nS = geoidH(la - d, lo), dl = d/Math.cos(phi), nE = geoidH(la, lo + dl), nW = geoidH(la, lo - dl);
                double x = -(nN - nS)/(2*dphi*M)*206264.806247, y = -(nE - nW)/(2*dphi*Nr)*206264.806247;     // xi = -dN/(M dphi), eta = -dN/(Nr cos(phi) dlambda)
                if (Math.abs(x) > 120 || Math.abs(y) > 120) throw new Exception("implausible result");
                runOnUiThread(() -> { settingDov = true; etXi.setText(String.format(Locale.US, "%.2f", x)); etEta.setText(String.format(Locale.US, "%.2f", y)); settingDov = false;
                    dovSrc = "NGS GEOID18 slope"; prefs.edit().putString("dovLatS", String.valueOf(la)).putString("dovLonS", String.valueOf(lo)).putString("dovSrcS", dovSrc).apply();
                    if (manual) Toast.makeText(this, String.format(Locale.US, "DoV from NGS: xi %.2f\" eta %.2f\"", x, y), Toast.LENGTH_LONG).show(); });
            } catch (Exception e) { if (manual) runOnUiThread(() -> Toast.makeText(this, "NGS deflection unavailable (offline, or outside the GEOID18 area: USA / Puerto Rico): " + e.getMessage(), Toast.LENGTH_LONG).show()); }
        }).start();
    }
    void vib() { try { Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) v.vibrate(VibrationEffect.createOneShot(120, VibrationEffect.DEFAULT_AMPLITUDE)); else v.vibrate(120); } catch (Exception e) {} }
    static double pn(EditText e) { try { return Double.parseDouble(e.getText().toString().trim()); } catch (Exception x) { return Double.NaN; } }
    TextView lbl(String t) { TextView x = new TextView(this); x.setText(t); x.setTextSize(18); x.setPadding(14, 0, 0, 0); extra.add(x); return x; }
    LinearLayout pair(View a, TextView l) { LinearLayout r = new LinearLayout(this); r.setGravity(Gravity.CENTER_VERTICAL); r.setPadding(0, 8, 0, 8);
        r.addView(a, new LinearLayout.LayoutParams(0, -2, 1)); r.addView(l, new LinearLayout.LayoutParams(170, -2)); return r; }
    TextView big(int sp) { TextView t = new TextView(this); t.setTextSize(sp); t.setTypeface(Typeface.MONOSPACE, Typeface.BOLD); t.setPadding(16,0,16,0); t.setSingleLine(true); return t; }
    EditText field(String hint, double v) { EditText e = new EditText(this); e.setHint(hint); e.setText(String.valueOf(v));
        
        e.setSingleLine(true); e.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED); return e; }

    SensorManager sm; Sensor baro; double baroHpa = Double.NaN; long hintT;
    SensorEventListener sl = new SensorEventListener() {        // phone barometer = station pressure (hPa), smoothed
        public void onSensorChanged(SensorEvent e) { double v = e.values[0]; if (v > 300 && v < 1100) baroHpa = Double.isNaN(baroHpa) ? v : baroHpa + 0.05*(v - baroHpa); }
        public void onAccuracyChanged(Sensor sn, int acc) {} };
    @Override protected void onResume() { super.onResume(); sm = (SensorManager) getSystemService(SENSOR_SERVICE); baro = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (baro != null) sm.registerListener(sl, baro, SensorManager.SENSOR_DELAY_NORMAL);
        if (!syncing && (ntpS == null || (SystemClock.elapsedRealtimeNanos() - ntpS[1])/1e9 > 600)) syncTime(); tick.run(); }
    @Override protected void onPause() { super.onPause(); h.removeCallbacks(tick); if (sm != null) sm.unregisterListener(sl); }
    Runnable tick = new Runnable() { public void run() { compute(); updateInfo(); sky.invalidate(); h.postDelayed(this, 30); } };

    // ---- Yale Bright Star Catalogue (J2000), downloaded once from VizieR and cached ----
    static final String HIP_URL = "https://vizier.cds.unistra.fr/viz-bin/asu-tsv?-source=I/239/hip_main&-out=HIP,RAICRS,DEICRS,Plx,pmRA,pmDE,Vmag&Vmag=%3C6.7&-out.max=9000";
    static String fetch(String url) throws IOException {
        java.net.HttpURLConnection c = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
        c.setConnectTimeout(15000); c.setReadTimeout(60000); return readAll(new InputStreamReader(c.getInputStream(), "UTF-8")); }
    void loadCatalog() {
        File cb = new File(getFilesDir(), "bsc5000.tsv"), ch = new File(getFilesDir(), "hip.tsv"); new File(getFilesDir(), "bsc.tsv").delete();
        new Thread(() -> {
            boolean hadB = cb.exists(), hadH = ch.exists(); String tb = null, th = null, err = null;
            try { tb = hadB ? readAll(new FileReader(cb)) : fetch(BSC_URL); } catch (Exception e) { err = "Star download failed (internet needed once): " + e; }
            try { th = hadH ? readAll(new FileReader(ch)) : fetch(HIP_URL); } catch (Exception e) { th = null; }
            final String fb = tb, fh = th, fe = err; final boolean nb2 = !hadB, nh2 = !hadH;
            runOnUiThread(() -> {
                if (fe != null) Toast.makeText(this, fe, Toast.LENGTH_LONG).show();
                int n = 0, hn = 0;
                try { if (fb != null) { n = applyBsc(fb); if (nb2) { FileWriter w = new FileWriter(cb); w.write(fb); w.close(); } } }
                catch (Exception e) { cb.delete(); Toast.makeText(this, "Catalog error: " + e.getMessage(), Toast.LENGTH_LONG).show(); return; }
                try { if (fb != null && fh != null) { hn = applyHip(fh); if (nh2) { FileWriter w = new FileWriter(ch); w.write(fh); w.close(); } } }
                catch (Exception e) { ch.delete(); Toast.makeText(this, "Hipparcos refinement unavailable: " + e.getMessage(), Toast.LENGTH_LONG).show(); }
                if (nb2 || nh2) Toast.makeText(this, "Added " + n + " stars, " + hn + " refined with Hipparcos", Toast.LENGTH_LONG).show();
            });
        }).start();
    }
    /** Replaces BSC/hand coordinates with Hipparcos astrometry (ICRS, epoch 1991.25 propagated to J2000 with proper motion) + parallax. */
    int applyHip(String t) throws Exception {
        String[] hd = null; int iR = -1, iD = -1, iP = -1, iA = -1, iB = -1, iV = -1, cnt = 0; double[][] rows = new double[12000][];
        for (String ln : t.split("\n")) {
            if (ln.startsWith("#") || ln.trim().isEmpty()) continue;
            String[] f = ln.split("\t", -1);
            if (hd == null) { hd = f; for (int k = 0; k < f.length; k++) { String c = f[k].trim();
                if (c.equals("RAICRS")) iR = k; else if (c.equals("DEICRS")) iD = k; else if (c.equals("Plx")) iP = k; else if (c.equals("pmRA")) iA = k; else if (c.equals("pmDE")) iB = k; else if (c.equals("Vmag")) iV = k; }
                if (iR<0||iD<0||iP<0||iA<0||iB<0||iV<0) throw new Exception("unexpected columns"); continue; }
            if (!f[0].trim().matches("\\d+") || f[iR].trim().isEmpty() || f[iD].trim().isEmpty() || f[iV].trim().isEmpty() || cnt >= rows.length) continue;
            double ra0 = num(f[iR]), de0 = num(f[iD]), pa = num(f[iA]), pb = num(f[iB]);
            double ra2 = (ra0 + pa/Math.cos(Math.toRadians(de0))*8.75/3.6e6 + 360) % 360, de2 = de0 + pb*8.75/3.6e6;
            rows[cnt++] = new double[]{ra2, de2, num(f[iP]), pa, pb, num(f[iV])};
        }
        Arrays.sort(rows, 0, cnt, (x, y) -> Double.compare(x[1], y[1]));
        int hit = 0;
        for (int i = 0; i < N; i++) {
            double rd = ra[i]*15, dd = dec[i], best = 0.02, cosd = Math.cos(Math.toRadians(dd)); int lo = 0, hi = cnt, bk = -1;
            while (lo < hi) { int mid = (lo + hi) >>> 1; if (rows[mid][1] < dd - 0.03) lo = mid + 1; else hi = mid; }
            for (int k = lo; k < cnt && rows[k][1] <= dd + 0.03; k++) {
                double dr = (((rows[k][0] - rd) + 540) % 360 - 180)*cosd, dn = rows[k][1] - dd, dist = Math.hypot(dr, dn);
                if (dist < best && Math.abs(rows[k][5] - mag[i]) < 1.0) { best = dist; bk = k; } }
            if (bk >= 0) { double[] r = rows[bk]; ra[i] = r[0]/15; dec[i] = r[1]; pmra[i] = r[3]; pmdec[i] = r[4]; plx[i] = Math.max(0, r[2])/1000.0; hip[i] = true; hit++; }
        }
        appT = 0; return hit;
    }
    static void reg(String raw, int idx, HashMap<String,Integer> k) {     // BSC name -> "AlpOri" / "58Ori" keys
        if (raw.length() < 3) return;
        String cons = raw.substring(raw.length()-3), rest = raw.substring(0, raw.length()-3).trim(); int d = 0;
        while (d < rest.length() && Character.isDigit(rest.charAt(d))) d++;
        String fl = rest.substring(0, d), by = rest.substring(d).replaceAll("\\s", "");
        if (!fl.isEmpty()) k.put(fl + cons, idx); if (!by.isEmpty()) k.put(by + cons, idx);
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
            rows.add(new Object[]{f[iHR].trim(), f[iN].trim().replaceAll("\\s+"," "), sex(f[iRA])*15, sex(f[iDE]), num(f[iV]), num(f[iPA])*1000, num(f[iPD])*1000, f[iN].trim()});
        }
        Collections.sort(rows, (a, b) -> Double.compare((double)a[4], (double)b[4]));
        HashSet<String> used = new HashSet<>(); for (int i = 0; i < N; i++) used.add(name[i]);
        int added = 0, nOld = N; HashMap<String,Integer> key = new HashMap<>();
        for (Object[] r : rows) {
            double rd = (double)r[2], dd = (double)r[3]; int m = -1;
            for (int j = 0; j < nOld && m < 0; j++) { double dr = (((ra[j]*15 - rd) + 540) % 360 - 180)*Math.cos(Math.toRadians(dd)), dn = dec[j]-dd;
                if (Math.hypot(dr, dn) < 0.05) m = j; }
            if (m >= 0) { if (!precise[m]) { ra[m]=rd/15; dec[m]=dd; pmra[m]=(double)r[5]; pmdec[m]=(double)r[6]; bsc[m]=true; } reg((String) r[7], m, key); continue; }
            if (N >= TARGET) continue;
            String n = ((String) r[1]).isEmpty() ? "HR" + r[0] : (String) r[1]; if (!used.add(n)) { n += " #" + r[0]; used.add(n); }
            name[N]=n; ra[N]=rd/15; dec[N]=dd; mag[N]=(double)r[4]; pmra[N]=(double)r[5]; pmdec[N]=(double)r[6]; bsc[N]=true; reg((String) r[7], N, key); N++; added++;
        }
        List<int[]> ns = new ArrayList<>();
        for (String c : CONS) { int e = c.indexOf('='); String cc = c.substring(0, e);
            for (String ch : c.substring(e+1).split(";")) { int prev = -1;
                for (String star : ch.trim().split(" ")) { Integer ix = key.get(star.contains("@") ? star.replace("@", "") : star + cc);
                    if (ix == null) { prev = -1; continue; } if (prev >= 0 && prev != ix) ns.add(new int[]{prev, ix}); prev = ix; } } }
        if (ns.size() > 100) { segs.clear(); segs.addAll(ns); }
        appT = 0; return added;
    }
    static double R(double deg) { return Math.toRadians(deg); }
    /** Apparent topocentric place: proper motion, IAU76 precession, IAU80 nutation (main terms),
     *  full annual aberration, sidereal time w/ equation of equinoxes, Saemundsson refraction. */
    double[] aApp = new double[MAX], dApp = new double[MAX], sD = new double[MAX], cD = new double[MAX], refr = new double[MAX], dab = new double[MAX]; long appT = 0; int appN = -1; double lstNow, gastH, dpsiAs, depsAs, latAUsed, lonAUsed, rfUsed; long lastMs; TextView[] tvInfo = new TextView[7];
    void compute() {
        long ms = now(); lastMs = ms;
        double jdUT = ms/86400000.0 + 2440587.5, jdTT = jdUT + 69.184/86400.0;
        double T = (jdTT-2451545.0)/36525.0, yrs = T*100;
        double[] nu = nutation(T); double dpsi = nu[0], deps = nu[1];
        double eps = R(epsA(T)/3600.0) + deps, ce = Math.cos(eps), se = Math.sin(eps);
        double[] eo = eopAt(jdUT - 2400000.5);                                          // IERS Rapid Service values for this instant (or typed overrides)
        double dut1 = Double.isNaN(manDut) ? eo[0] : manDut, xp = Double.isNaN(manXp) ? eo[1] : manXp, yp = Double.isNaN(manYp) ? eo[2] : manYp;
        curDut = dut1; curXp = xp; curYp = yp; autoDut = eo[0]; autoXp = eo[1]; autoYp = eo[2]; eopOk = eo[3] > 0;
        double d = jdUT + dut1/86400.0 - 2451545.0, Tu = d/36525.0;                       // sidereal time uses UT1 = UTC + DUT1
        double gmst = 280.46061837 + 360.98564736629*d + 0.000387933*Tu*Tu - Tu*Tu*Tu/38710000.0;
        double lamG = R(lon), pmLat = xp*Math.cos(lamG) - yp*Math.sin(lamG), pmLon = Math.tan(R(lat))*(xp*Math.sin(lamG) + yp*Math.cos(lamG));   // polar motion (arcsec)
        double latA = lat + (xi + pmLat)/3600.0, lonA = lon + (eta/Math.cos(R(lat)) + pmLon)/3600.0;      // astronomic lat/lon = geodetic + deflection of the vertical + polar motion
        double lst = R(gmst + lonA) + dpsi*ce, p = R(latA), sp = Math.sin(p), cp = Math.cos(p), as = 1.0/206264.806247;
        lstNow = lst; latAUsed = latA; lonAUsed = lonA; dpsiAs = Math.toDegrees(dpsi)*3600.0; depsAs = Math.toDegrees(deps)*3600.0;
        { double gr = (lst - R(lonA)) % (2*Math.PI); if (gr < 0) gr += 2*Math.PI; gastH = gr*12/Math.PI; }
        if (ms - appT > 5000 || appN != N) {            // apparent places change slowly: refresh every 5 s
            appT = ms; appN = N;
            double[][] Q = precNut(T, dpsi, deps);                                // IAU 2006 precession + nutation matrix (ICRS -> true of date)
            double M = R(357.52911+35999.05029*T), C = (1.914602-0.004817*T)*Math.sin(M)+0.019993*Math.sin(2*M)+0.000289*Math.sin(3*M);
            double sunL = R(280.46646+36000.76983*T+C), ecc = 0.016708634-0.000042037*T, per = R(102.93735+1.71946*T), kap = 20.49552*as;
            double nuS = M + R(C), Rs = 1.000001018*(1 - ecc*ecc)/(1 + ecc*Math.cos(nuS)), SX = Rs*Math.cos(sunL), SY = Rs*Math.sin(sunL)*ce, SZ = Rs*Math.sin(sunL)*se;   // Sun geocentric (AU)
            for (int i = 0; i < N; i++) {
                double a0 = R(ra[i]*15), d0 = R(dec[i]);
                a0 += pmra[i]/1000.0*as/Math.cos(d0)*yrs; d0 += pmdec[i]/1000.0*as*yrs;
                double cd0 = Math.cos(d0), x0 = cd0*Math.cos(a0), y0 = cd0*Math.sin(a0), z0 = Math.sin(d0);
                double xx = Q[0][0]*x0 + Q[0][1]*y0 + Q[0][2]*z0, yy = Q[1][0]*x0 + Q[1][1]*y0 + Q[1][2]*z0, zq = Q[2][0]*x0 + Q[2][1]*y0 + Q[2][2]*z0;
                double a = Math.atan2(yy, xx), dd = Math.asin(zq);
                double sa = Math.sin(a), ca = Math.cos(a), sd = Math.sin(dd), cd = Math.cos(dd);
                if (plx[i] > 0) {                                                       // annual parallax: displacement toward the Sun's direction
                    double pr = plx[i]*as, dA = pr*(-SX*sa + SY*ca)/cd, dD = pr*(-SX*ca*sd - SY*sa*sd + SZ*cd);
                    pshift[i] = Math.hypot(dA*cd, dD)/as; a += dA; dd += dD;
                    sa = Math.sin(a); ca = Math.cos(a); sd = Math.sin(dd); cd = Math.cos(dd);
                } else pshift[i] = 0;
                for (int k = 0; k < 2; k++) {
                    double lam = k == 0 ? sunL : per, f = k == 0 ? -kap : ecc*kap;
                    a += f*(ca*Math.cos(lam)*ce + sa*Math.sin(lam))/cd;
                    dd += f*(Math.cos(lam)*ce*(se/ce*cd - sa*sd) + ca*sd*Math.sin(lam));
                }
                aApp[i] = a; dApp[i] = dd; sD[i] = Math.sin(dd); cD[i] = Math.cos(dd);
            }
        }
        double mer = Double.isNaN(azRef) ? 0 : azRef, rf = refFactor(),
               beta = 7.2921150e-5*(6378137.0/Math.sqrt(1 - 0.00669437999014*Math.pow(Math.sin(p), 2)) + elev)*Math.cos(p)/299792458.0;   // v/c of the observer's rotation
        rfUsed = rf;   // chart faces north; refraction scale for pressure/temperature
        for (int i = 0; i < N; i++) {
            double H0 = lst - aApp[i], sH0 = Math.sin(H0), cH0 = Math.cos(H0);
            double dAl = beta*cH0/cD[i], dDe = beta*sH0*sD[i];                               // diurnal aberration (<= 0.32")
            double sDp = sD[i] + cD[i]*dDe, cDp = cD[i] - sD[i]*dDe, H = H0 - dAl, cH = Math.cos(H), sH = Math.sin(H);
            double h = Math.toDegrees(Math.asin(sDp*sp + cDp*cp*cH));
            az[i] = (Math.toDegrees(Math.atan2(sH, cH*sp - sDp/cDp*cp)) + 180 + 360) % 360;
            double h0 = h; if (h > -1) h += rf*1.02/Math.tan(R(h+10.3/(h+5.11)))/60.0;
            refr[i] = (h - h0)*3600.0; dab[i] = beta*Math.hypot(cH0, sH0*sD[i])*206264.806247;     // refraction applied and diurnal aberration size (arcsec)
            alt[i] = h; off[i] = ((az[i] - mer + 540) % 360) - 180;
        }
    }
    /** Time until the star next crosses the drawn (north) meridian; falls back to the south half if it never crosses the north half. */
    // ---- Atomic-clock time: SNTP against NIST / Google / pool servers, kept running on the monotonic clock ----
    volatile double[] ntpS = null; volatile boolean syncing;           // {server time ms at sync, elapsedRealtimeNanos at sync, +/- error ms}
    long now() { double[] q = ntpS; return q != null ? Math.round(q[0] + (SystemClock.elapsedRealtimeNanos() - q[1])/1e6) : System.currentTimeMillis(); }
    static double ntpMs(byte[] b, int o) { long sec = 0, fr = 0; for (int i = 0; i < 4; i++) sec = (sec << 8) | (b[o+i] & 0xff); for (int i = 4; i < 8; i++) fr = (fr << 8) | (b[o+i] & 0xff);
        return (sec - 2208988800L)*1000.0 + fr*1000.0/4294967296.0; }
    void syncTime() {
        if (syncing) return; syncing = true; syncB.setText("Syncing...");
        new Thread(() -> {
            List<double[]> smp = new ArrayList<>(); long lastE = 0;                                   // {round-trip delay ms, server-minus-monotonic offset ms}
            for (String host : new String[]{"time.nist.gov", "time.google.com", "time.cloudflare.com", "pool.ntp.org"}) {
                int ok = 0;
                for (int k = 0; k < 6; k++) {
                    try { java.net.DatagramSocket so = new java.net.DatagramSocket(); so.setSoTimeout(1500);
                        byte[] b = new byte[48]; b[0] = 0x1B; java.net.InetAddress ad = java.net.InetAddress.getByName(host);
                        long e0 = SystemClock.elapsedRealtimeNanos(); so.send(new java.net.DatagramPacket(b, 48, ad, 123));
                        java.net.DatagramPacket rp = new java.net.DatagramPacket(b, 48); so.receive(rp); long e3 = SystemClock.elapsedRealtimeNanos(); so.close();
                        if ((b[0] & 7) != 4 || b[1] == 0) continue;
                        double t2 = ntpMs(b, 32), t3 = ntpMs(b, 40), delay = (e3 - e0)/1e6 - (t3 - t2);
                        if (delay < 0 || delay > 1000) continue;
                        smp.add(new double[]{delay, (t3 + delay/2) - e3/1e6}); lastE = e3; ok++;
                    } catch (Exception ex) {}
                    if (k == 1 && ok == 0) break;                                                    // host unreachable: skip it
                    try { Thread.sleep(200); } catch (Exception ex) {}
                }
            }
            if (!smp.isEmpty()) {
                Collections.sort(smp, (p1, p2) -> Double.compare(p1[0], p2[0]));                      // NTP clock filter: keep the lowest-delay samples, average them
                double md = smp.get(0)[0], ref = smp.get(0)[1], sum = 0, sq = 0; int n = 0;
                for (double[] q : smp) { if (q[0] > 1.5*md + 5 || n >= 10) break; double dv = q[1] - ref; sum += dv; sq += dv*dv; n++; }
                double mean = sum/n, sd = Math.sqrt(Math.max(0, sq/n - mean*mean));
                ntpS = new double[]{ref + mean + lastE/1e6, (double) lastE, md/2, sd, n};          // {server ms at lastE, lastE ns, +/- bound ms, jitter ms, samples used}
            }
            syncing = false;
            runOnUiThread(() -> { double[] q = ntpS; syncB.setText(q != null ? String.format(Locale.US, "Sync \u00b1%.0fms", q[2]) : "Sync x"); });
        }).start();
    }
    // ---- IERS Earth orientation (UT1-UTC, polar motion) from the USNO rapid-service daily file ----
    static final String EOP_URL = "https://maia.usno.navy.mil/ser7/finals2000A.daily";
    volatile double[][] eop; double manDut = Double.NaN, manXp = Double.NaN, manYp = Double.NaN, curDut, curXp, curYp, autoDut, autoXp, autoYp; boolean eopOk;
    EditText etDut, etXp, etYp; TextView tvEop;
    double[] eopAt(double mjd) {                                   // {UT1-UTC s, xp ", yp ", valid}
        double[][] e = eop; if (e == null || e.length < 2) return new double[]{0, 0, 0, 0};
        int hi = e.length - 1; if (mjd > e[hi][0] + 120 || mjd < e[0][0] - 120) return new double[]{0, 0, 0, 0};
        if (mjd <= e[0][0]) return new double[]{e[0][3], e[0][1], e[0][2], 1};
        if (mjd >= e[hi][0]) return new double[]{e[hi][3], e[hi][1], e[hi][2], 1};
        int lo = 0; while (hi - lo > 1) { int mid = (lo + hi) >>> 1; if (e[mid][0] <= mjd) lo = mid; else hi = mid; }
        double f = (mjd - e[lo][0])/(e[hi][0] - e[lo][0]);
        if (Math.abs(e[hi][3] - e[lo][3]) > 0.5) f = 0;                                    // leap-second step: do not interpolate across it
        return new double[]{e[lo][3] + f*(e[hi][3] - e[lo][3]), e[lo][1] + f*(e[hi][1] - e[lo][1]), e[lo][2] + f*(e[hi][2] - e[lo][2]), 1};
    }
    void loadEop() {
        File cf = new File(getFilesDir(), "eop.txt");
        new Thread(() -> {
            String t = null;
            if (!cf.exists() || System.currentTimeMillis() - cf.lastModified() > 12*3600*1000L) {
                try { t = fetch(EOP_URL); FileWriter w = new FileWriter(cf); w.write(t); w.close(); } catch (Exception e) { t = null; } }
            try { if (t == null && cf.exists()) t = readAll(new FileReader(cf)); } catch (Exception e) { t = null; }
            if (t == null) return;
            List<double[]> rows = new ArrayList<>();
            for (String ln : t.split("\n")) { if (ln.length() < 68) continue;
                try { double mjd = Double.parseDouble(ln.substring(7, 15).trim()), x = Double.parseDouble(ln.substring(18, 27).trim()),
                        y = Double.parseDouble(ln.substring(37, 46).trim()), u = Double.parseDouble(ln.substring(58, 68).trim());
                    rows.add(new double[]{mjd, x, y, u}); } catch (Exception e) {} }
            if (rows.size() > 2) eop = rows.toArray(new double[0][]);
        }).start();
    }
    String pressSrc() { return !Double.isNaN(pressHpa) ? "set" : !Double.isNaN(baroHpa) ? "baro" : "est"; }
    String catQ(int i) { return hip[i] ? "hip" : precise[i] ? "hand" : bsc[i] ? "bsc" : "approx"; }
    double usedPressure() { return !Double.isNaN(pressHpa) ? pressHpa : !Double.isNaN(baroHpa) ? baroHpa : 1013.25*Math.pow(1 - 2.25577e-5*elev, 5.25588); }   // typed value > phone barometer > standard atmosphere
    double refFactor() { return (usedPressure()/1010.0)*(283.0/((tempF-32)*5.0/9.0 + 273.15)); }
    String merText(int i) {
        double TP = 2*Math.PI, H = lstNow - aApp[i], w = R(360.98564736629)/86400.0;
        double t0 = (((-H) % TP) + TP) % TP / w, t1 = (((Math.PI - H) % TP) + TP) % TP / w;     // HA = 0 (upper), HA = 12h (lower)
        boolean n0 = dApp[i] > R(lat), n1 = lat > 0;                                          // which of them lies on the north half
        boolean up = (n0 && n1) ? t0 <= t1 : n0 ? true : n1 ? false : t0 <= t1;
        double t = up ? t0 : t1; boolean north = up ? n0 : n1;
        double hc = up ? 0 : Math.PI, al = Math.toDegrees(Math.asin(sD[i]*Math.sin(R(lat)) + cD[i]*Math.cos(R(lat))*Math.cos(hc)));
        long ten = Math.round(t*10);
        return String.format(Locale.US, "Mer %02d:%02d:%04.1f %s%s %.1f°", ten/36000, (ten/600)%60, (ten%600)/10.0, north ? "N " : "S ", up ? "upper" : "lower", al);
    }
    boolean vis(int i) { return alt[i] >= 5 && alt[i] <= 55; }     // chart now spans all 360 deg of azimuth
    int selIdx() { if (sel == null) return -1; for (int i = 0; i < N; i++) if (name[i].equals(sel)) return i; return -1; }
    void updateInfo() {
        long now = now(); Calendar c = Calendar.getInstance(); c.setTimeInMillis(now);
        tvTime.setText(String.format(Locale.US, "Time %02d:%02d:%02d.%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), (int)((now % 1000) / 10)));
        int i = selIdx();
        if (i < 0) { tvName.setText("Tap a star"); tvAz.setText("Az   --"); tvAlt.setText("Alt  --"); tvZen.setText("90-Alt --"); tvMer.setText("Mer --"); }
        else { tvName.setText(String.format(Locale.US, "%s  mag %.2f  %s%s", name[i], mag[i], hip[i] ? "±1 mas (Hip)" : precise[i] ? "±0.1\"" : bsc[i] ? "±1\" (BSC)" : "±1\u2032 approx", vis(i) ? "" : "  (outside window)"));
            tvAz.setText(Double.isNaN(azRef) ? "Az   " + dms(az[i]) : "rAz  " + dms(off[i])); tvAlt.setText("Alt  " + dms(alt[i])); tvZen.setText("90-Alt " + dms(90 - alt[i])); tvMer.setText(merText(i)); }
        fit(tvTime, 28); fit(tvZen, 30); fit(tvName, 16); fit(tvAz, 30); fit(tvAlt, 30); fit(tvMer, 26); updateGeo();
        if (now - hintT > 1000) { hintT = now; etPress.setHint(String.format(Locale.US, "%.2f %s", usedPressure()/33.8639, !Double.isNaN(pressHpa) ? "set" : !Double.isNaN(baroHpa) ? "baro" : "est")); }
    }
    static String dms3(double deg) {          // plain "D M S.sss" for spreadsheets
        long m = Math.round(Math.abs(deg)*3600000.0);
        return String.format(Locale.US, "%s%d %02d %06.3f", deg < 0 ? "-" : "", m/3600000, (m/60000)%60, (m%60000)/1000.0);
    }
    void record() {
        try {
            compute();                                   // refresh so everything matches the timestamp exactly
            long ms = lastMs; Date dt = new Date(ms);
            File f = new File(getExternalFilesDir(null), "star_log8.csv"); boolean nw = !f.exists();
            FileWriter w = new FileWriter(f, true);
            if (nw) w.write("utc,local,unix_s,time_src,clock_err_ms,clock_jitter_ms,clock_samples,lat_deg,lon_deg,elev_m,temp_F,press_hPa,press_src,"
                + "star,mag,catalog,ra_j2000_deg,dec_j2000_deg,pm_ra_mas_yr,pm_dec_mas_yr,plx_mas,plx_shift_as,app_ra_deg,app_dec_deg,"
                + "az_deg,alt_deg,zen_deg,az_dms,alt_dms,az_rel_prev_zero_deg,refr_as,diurnal_ab_as,"
                + "dov_xi_as,dov_eta_as,dov_src,astro_lat_deg,astro_lon_deg,dut1_s,xp_as,yp_as,dpsi_as,deps_as,gast_h,model\n");
            SimpleDateFormat u = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US); u.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat l = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.US);
            double[] qn = ntpS; int n = 0, sIdx = selIdx();
            for (int i = 0; i < N; i++) if (sIdx >= 0 ? i == sIdx : sky.onScreen(i)) {
                String rel = Double.isNaN(azRef) ? "" : String.format(Locale.US, "%.8f", ((az[i] - azRef + 540) % 360) - 180);
                StringBuilder sb = new StringBuilder();
                sb.append(String.format(Locale.US, "%s,%s,%.3f,%s,%.1f,%.2f,%d,", u.format(dt), l.format(dt), ms/1000.0, qn != null ? "ntp" : "device", qn != null ? qn[2] : -1.0, qn != null ? qn[3] : -1.0, qn != null ? (int) qn[4] : 0));
                sb.append(String.format(Locale.US, "%.8f,%.8f,%.1f,%.1f,%.2f,%s,", lat, lon, elev, tempF, usedPressure(), pressSrc()));
                sb.append(String.format(Locale.US, "\"%s\",%.2f,%s,%.9f,%.9f,%.3f,%.3f,%.3f,%.4f,%.8f,%.8f,", name[i], mag[i], catQ(i), ra[i]*15, dec[i], pmra[i], pmdec[i], plx[i]*1000, pshift[i], Math.toDegrees(aApp[i]), Math.toDegrees(dApp[i])));
                sb.append(String.format(Locale.US, "%.8f,%.8f,%.8f,%s,%s,%s,%.3f,%.3f,", az[i], alt[i], 90 - alt[i], dms3(az[i]), dms3(alt[i]), rel, refr[i], dab[i]));
                sb.append(String.format(Locale.US, "%.2f,%.2f,%s,%.8f,%.8f,%.4f,%.4f,%.4f,%.4f,%.4f,%.6f,%s\n", xi, eta, dovSrc, latAUsed, lonAUsed, curDut, curXp, curYp, dpsiAs, depsAs, gastH,
                    "IAU2006prec;IAU1980nut63;plx;aberr+diurnal;Saemundsson;polarmotion;DUT1"));
                w.write(sb.toString()); n++; }
            w.close();
            if (sIdx >= 0) { azRef = az[sIdx]; sky.cO = 0; sky.invalidate(); }     // recorded azimuth becomes the new zero
            vib(); Toast.makeText(this, "Saved " + n + " rows to\n" + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) { Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show(); }
    }
    class Pager extends HorizontalScrollView { Pager(Context c) { super(c); } @Override public void fling(int v) {} }   // no fling: pages snap
    class Sky extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG); float s = 1, base = 1, cx, cy; double zoom = 1, cO = 0, cA = 30;   // ONE scale (px per degree) for both axes -> true shapes
        ScaleGestureDetector sg; GestureDetector gd;
        Sky(Context c) {
            super(c);
            sg = new ScaleGestureDetector(c, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override public boolean onScale(ScaleGestureDetector d) {
                    double o = cO + (d.getFocusX()-cx)/s, a = cA - (d.getFocusY()-cy)/s;           // sky point under the fingers
                    zoom = Math.max(1, Math.min(30, zoom*d.getScaleFactor())); s = base*(float)zoom;
                    cO = wrap(o - (d.getFocusX()-cx)/s); cA = a + (d.getFocusY()-cy)/s; clamp(); invalidate(); return true; } });
            gd = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onDown(MotionEvent e) { return true; }
                @Override public boolean onScroll(MotionEvent a, MotionEvent b, float dx, float dy) {
                    if (sg.isInProgress()) return false; cO = wrap(cO + dx/s); cA -= dy/s; clamp(); invalidate(); return true; }
                @Override public boolean onDoubleTap(MotionEvent e) { zoom = 1; cO = 0; cA = 30; invalidate(); return true; }
                @Override public boolean onSingleTapUp(MotionEvent e) { pick(e.getX(), e.getY()); return true; } });
        }
        void lay() { base = getHeight()/50f; s = base*(float)zoom; cx = getWidth()/2f; cy = getHeight()/2f; }   // zoom 1 = altitude 5..55 fills the height
        void clamp() { double half = getHeight()/(2.0*s); cA = Math.max(5 + half, Math.min(55 - half, cA)); }
        double wrap(double d) { d %= 360; if (d > 180) d -= 360; if (d < -180) d += 360; return d; }
        float X(double o) { return cx + (float)wrap(o - cO)*s; }
        float Y(double a) { return cy + (float)(cA - a)*s; }
        boolean onScreen(int i) { float x = X(off[i]), y = Y(alt[i]); return x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight(); }
        @Override protected void onDraw(Canvas c) {
            lay(); clamp(); int W = getWidth(), H = getHeight(); float dn = getResources().getDisplayMetrics().density, ts = 11*dn;
            c.drawColor(cBg); p.setTextSize(ts); p.setStyle(Paint.Style.FILL); p.setStrokeWidth(1);
            double ro = Double.isNaN(azRef) ? 0 : azRef;
            int st = 90; for (int q : new int[]{1,2,5,10,15,30,45,90}) if (q*s >= 45) { st = q; break; }
            for (int az = 0; az < 360; az += st) { float x = X(wrap(az)); if (x < -2 || x > W+2) continue; p.setColor(cGrid); c.drawLine(x, 0, x, H, p); c.drawText(az+"\u00b0", x+3, ts+2, p); }
            int ast = 10; for (int q : new int[]{1,2,5,10}) if (q*s >= 12*dn) { ast = q; break; }
            for (int a = 5; a <= 55; a++) if (a % ast == 0) { float y = Y(a); if (y < 0 || y > H) continue;
                p.setColor(cAltLn); p.setStrokeWidth(2); c.drawLine(0, y, W, y, p);
                p.setColor(cAltTx); p.setTextSize(ts*1.2f); p.setFakeBoldText(true); String l1 = (90-a)+"\u00b0";
                c.drawText(l1, 4, y-4, p); c.drawText(l1, W-4-p.measureText(l1), y-4, p); p.setFakeBoldText(false); p.setTextSize(ts); p.setStrokeWidth(1); }
            p.setColor(cAltTx); p.setFakeBoldText(true); p.setTextSize(ts*1.3f); c.drawText("90-Alt", 4, H-6, p); c.drawText("90-Alt", W-4-p.measureText("90-Alt"), H-6, p); p.setFakeBoldText(false); p.setTextSize(ts);
            float xn = X(wrap(-ro)); p.setColor(cMer); p.setStrokeWidth(3); c.drawLine(xn, 0, xn, H, p); p.setStrokeWidth(1); c.drawText("N Meridian", xn+6, 2.6f*ts, p);
            if (!Double.isNaN(azRef)) { float xf = X(0); p.setColor(cFalse); p.setStrokeWidth(3); p.setPathEffect(new DashPathEffect(new float[]{16, 10}, 0));
                c.drawLine(xf, 0, xf, H, p); p.setPathEffect(null); p.setStrokeWidth(1); c.drawText("False N (zero)", xf+6, 4f*ts, p); }
            p.setColor(cLine); p.setStrokeWidth(2);
            for (int[] g : segs) { double df = wrap(off[g[1]] - off[g[0]]); if (Math.abs(df) > 60) continue;
                float x1 = X(off[g[0]]); c.drawLine(x1, Y(alt[g[0]]), x1 + (float)df*s, Y(alt[g[1]]), p); }
            int si = selIdx(); double lblMag = zoom < 2 ? 2.2 : 3.0;
            for (int i = 0; i < N; i++) {
                float x = X(off[i]), y = Y(alt[i]); if (x < -20 || x > W+20 || y < -20 || y > H+20) continue;
                float r = Math.max(1.2f, (5.2f - (float)mag[i])*0.85f*dn);
                p.setColor(cStar); p.setStyle(Paint.Style.FILL); c.drawCircle(x, y, r, p);
                if (mag[i] < lblMag) { p.setColor(cLbl); c.drawText(name[i], x + r + 4, y - r, p); }
                if (i == si) { p.setColor(cSel); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(3); c.drawCircle(x, y, r + 10, p); p.setStyle(Paint.Style.FILL); }
            }
            p.setColor(cTx); p.setStyle(Paint.Style.FILL); p.setStrokeWidth(1); p.setFakeBoldText(true); p.setTextSize(ts*1.6f);
            String[] cn = {"N","E","S","W"}; for (int k = 0; k < 4; k++) { float x = X(wrap(90*k - ro)); if (x > 0 && x < W) c.drawText(cn[k], x - p.measureText(cn[k])/2, H - 6, p); }
            p.setFakeBoldText(false); p.setTextSize(ts); c.drawText(String.format(Locale.US, "x%.1f  %.0f\u00b0 x %.0f\u00b0 (1:1)", zoom, W/s, H/s), W/2f - 7*ts, H - 6 - 2*ts, p);
        }
        void pick(float px, float py) {
            float best = 14*getResources().getDisplayMetrics().density; int bi = -1;
            for (int i = 0; i < N; i++) { if (!vis(i)) continue; float d = (float)Math.hypot(X(off[i])-px, Y(alt[i])-py); if (d < best) { best = d; bi = i; } }
            if (bi >= 0) sel = name[bi]; updateInfo(); invalidate();
        }
        @Override public boolean onTouchEvent(MotionEvent e) { getParent().requestDisallowInterceptTouchEvent(true); lay(); sg.onTouchEvent(e); gd.onTouchEvent(e); return true; }
    }
}
