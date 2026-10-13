# Meridian Sky

Real-time star chart for Android 7+ (built for a Samsung S8). Shows altitude 5°–55° across the full 360° of azimuth, with constellation lines, live az/alt readouts, time-to-meridian, and a CSV recorder.

## Build the APK on GitHub
1. Push this folder (including the hidden `.github` folder) to a GitHub repo.
2. Open **Actions → Build APK** (runs on every push, or press *Run workflow*).
3. When it is green, download the **MeridianSky-debug-apk** artifact (a zip containing `app-debug.apk`).
4. Copy the APK to the phone and install it (allow "Install unknown apps").

No Gradle wrapper is included; the workflow installs Gradle 7.5 and JDK 17 itself. To build locally, open the folder in Android Studio.

## First launch
- Needs internet once: it downloads the Yale Bright Star Catalogue from VizieR and keeps the **5000 brightest stars** (cached on the phone afterwards). If the download fails you get ~100 built-in stars; it retries on the next launch.
- It also syncs the clock to an atomic-clock time server (see Time).

## Screens
Swipe left/right on the readouts to switch screens.

**Screen 1 – readouts + chart**
- `Time`: local time to hundredths of a second.
- Selected star: name, magnitude, catalog accuracy.
- `Az` (or `rAz` when a zero is set) and `90-Alt` (90 − refracted altitude), in degrees–minutes–seconds to 0.01″. The altitude itself is not shown but is saved in the CSV (`alt_deg`, with `zen_deg` = 90 − alt).
- `Mer`: time until the star next crosses the north meridian (upper/lower culmination, with its altitude then).
- **Tap anywhere on the readouts to Record** (the phone vibrates).
- Chart: pinch sideways = zoom azimuth, pinch up/down = zoom altitude, drag to scroll, double-tap to reset, tap a star to select it. The altitude grid lines are labelled with 90 − altitude (`90-Alt`) on both edges. N/E/S/W letters along the bottom. Chart faces north with the north meridian as the solid line.

**Screen 2 – inputs**
- Latitude (°), longitude (°, east positive), elevation (m), temperature (°F), pressure (inHg).
- Pressure: type a station-pressure value, or leave blank to use the phone barometer (or a standard-atmosphere estimate from elevation if there is no barometer). The empty box shows the value in use and its source (`baro`, `est`, `set`).
- Below the buttons it shows the length of 1° of latitude and of 1° of longitude at your latitude (feet and miles, WGS84 ellipsoid, including elevation) and the Earth's rotation speed there in ft/s and mph.
- **Lock** freezes the input fields (Done on the keyboard also locks).
- **Sync** re-syncs the clock. **Day/Night** toggles night mode (red on black is the default). **Record** and **Clear zero**.

## Azimuth zero
Recording with a star selected makes that star's azimuth the new zero: all azimuths are shown relative to it (`rAz`), the chart re-centres on it, and a dashed "False N (zero)" line is drawn. The real north meridian stays as the solid line and the N/E/S/W letters stay at their true positions. **Clear zero** returns to true north. Recording with no star selected does not change the zero.

## Recording
Saved to `Android/data/com.example.meridian/files/star_log5.csv`. With a star selected it logs that star; otherwise it logs every star currently on screen.

Columns: `utc, local, unix_s, lat_deg, lon_deg, star, mag, az_deg, alt_deg, zen_deg, az_dms, alt_dms, az_rel_prev_zero_deg, elev_m, temp_F, press_hPa, time_src, clock_err_ms`
- Times to the millisecond; az/alt to 8 decimals plus `D M S.sss`.
- `zen_deg` = 90 − alt. `az_rel_prev_zero_deg` = azimuth relative to the zero in effect before this record (blank on the first).
- `time_src` is `ntp` or `device`; `clock_err_ms` is the worst-case sync error.

## Accuracy and methods
- Apparent places: proper motion, IAU 1976 precession, nutation (main IAU 1980 terms), annual aberration, equation of the equinoxes. Not modelled: annual parallax, deflection of the vertical.
- Refraction (Saemundsson) scaled by pressure and temperature.
- Time: SNTP against time.nist.gov, time.google.com and pool.ntp.org, then kept on the phone's monotonic clock. Syncs at launch and every 10 min; works offline after a sync. Some mobile networks block NTP – try Wi-Fi.
- Star data: ~37 bright stars use hand-entered Hipparcos-class coordinates (±0.1″); the rest use Yale BSC (about ±1″). Stars not matched to the catalog are marked "approx".
- Constellation stick figures for all 88 constellations were written from memory (Bayer/Flamsteed IDs); some faint southern figures may differ from IAU/Stellarium. They only appear for stars present in the downloaded catalog.
- Accuracy is limited by the phone clock/network latency, your entered position, and weather.

## Files
- `app/src/main/java/com/example/meridian/MainActivity.java` – the whole app
- `app/src/main/AndroidManifest.xml` – permissions: INTERNET, VIBRATE; portrait only
- `.github/workflows/build.yml` – GitHub Actions APK build
