# Meridian Sky

Real-time star chart for Android 7+ (built for a Samsung S8). Altitude 5°–55° over the full 360° of azimuth, constellation lines for all 88 constellations, live az/alt readouts, time-to-meridian, and a CSV recorder that logs every correction.

## Build the APK on GitHub
1. Push this folder (including the hidden `.github` folder) to a GitHub repo.
2. Open **Actions → Build APK** (runs on every push, or press *Run workflow*).
3. When it is green, download the **MeridianSky-debug-apk** artifact (a zip containing `app-debug.apk`).
4. Copy the APK to the phone and install it (allow "Install unknown apps").

No Gradle wrapper is included; the workflow installs Gradle 7.5 and JDK 17. To build locally, open the folder in Android Studio.

## First launch (internet needed once)
- Downloads the Yale Bright Star Catalogue (VizieR) and keeps the **5000 brightest stars**, then downloads Hipparcos astrometry to refine positions, proper motions and parallaxes. Both are cached.
- Syncs the clock to NTP servers and downloads the IERS Earth-orientation file (UT1−UTC, polar motion; refreshed every 12 h).
- If a download fails, the app falls back to what it has (about 100 built-in stars) and retries next launch.

## Screen 1 – readouts and chart (swipe left for inputs)
- `Time`: local time to 0.01 s. Star name, magnitude and catalog accuracy.
- `Az` (or `rAz` when a zero is set) and `90-Alt` (90 − refracted altitude) in degrees–minutes–seconds to 0.01″. The altitude itself is not shown but is recorded.
- `Mer`: time until the star next crosses the north meridian (upper/lower culmination and its altitude then).
- **Tap anywhere on the readouts to Record** (the phone vibrates).
- Chart: fixed 1:1 scale (equal degrees per pixel on both axes). Pinch to zoom, drag to scroll (azimuth wraps all the way round), double-tap to reset (about 50° × 50° centred on north), tap a star to select it. The grid lines are labelled with 90 − altitude on both edges. N/E/S/W letters sit along the bottom.

## Screen 2 – inputs and all the numbers (swipe right to return)
Inputs (blank = automatic where noted):
- Latitude, longitude, elevation (m), temperature (°F), pressure (inHg; blank = phone barometer, or a standard-atmosphere estimate if there is no barometer).
- ξ and η, the deflection of the vertical (arcsec). **DoV from NGS** fills them for the USA/Puerto Rico (see Methods).
- DUT1, xp, yp overrides (blank = IERS automatic).
- Buttons: **Lock** (freezes the inputs; also auto-fetches DoV when the locked position has moved more than ~1 km), **Sync** (re-syncs the clock), **Day/Night**, **Record**, **Clear zero**.

Information lines shown on this screen:
- Length of 1° of latitude and of longitude at your latitude (ft and miles, WGS84 including elevation) and the Earth-rotation speed there (ft/s, mph).
- Deflection of the vertical (ξ, η, tilt), its source, and the resulting astronomic latitude/longitude.
- Annual parallax of the selected star and today's shift.
- IERS values in use (UT1−UTC, pole x/y).
- Clock: NTP error bound, jitter, samples used, age of the sync.
- Refraction: pressure (and its source), temperature, scale factor; for the selected star, the refraction applied and its diurnal aberration.
- Selected star's catalog quality and proper motion.
- Nutation Δψ, Δε; Greenwich apparent sidereal time; astronomic latitude.
- A one-line model summary.

## Azimuth zero
Recording with a star selected makes that star's azimuth the new zero: all azimuths are shown relative to it (`rAz`), the chart re-centres on it, and a dashed "False N (zero)" line is drawn. The real north meridian stays as the solid line and the N/E/S/W letters stay at their true positions. **Clear zero** returns to true north. Recording with no star selected does not change the zero.

## Recording
Saved to `Android/data/com.example.meridian/files/star_log8.csv`. With a star selected it logs that star; otherwise every star currently on screen. Each row stores everything used for that star at that instant:

- Time: `utc, local, unix_s, time_src, clock_err_ms, clock_jitter_ms, clock_samples`
- Site and atmosphere: `lat_deg, lon_deg, elev_m, temp_F, press_hPa, press_src`
- Star: `star, mag, catalog (hip/hand/bsc/approx), ra_j2000_deg, dec_j2000_deg, pm_ra_mas_yr, pm_dec_mas_yr, plx_mas, plx_shift_as, app_ra_deg, app_dec_deg`
- Result: `az_deg, alt_deg, zen_deg (90−alt), az_dms, alt_dms, az_rel_prev_zero_deg`
- Corrections: `refr_as, diurnal_ab_as, dov_xi_as, dov_eta_as, dov_src, astro_lat_deg, astro_lon_deg, dut1_s, xp_as, yp_as, dpsi_as, deps_as, gast_h`
- `model`: short description of the algorithms.

Times are to the millisecond; az/alt to 8 decimals plus `D M S.sss`.

## Methods and accuracy
- Apparent places: Hipparcos/BSC position and proper motion → IAU 2006 bias-precession (Fukushima–Williams angles) + 63-term IAU 1980 nutation → annual parallax → annual aberration → diurnal aberration (observer's rotation, ≤0.32″) → sidereal time from UT1 (with the equation of the equinoxes) → astronomic latitude/longitude (geodetic + deflection of the vertical + polar motion) → refraction.
- Refraction: Saemundsson formula scaled by pressure and temperature. Accurate to a few arcseconds above ~15° altitude; below that, weather dominates and errors can reach tens of arcseconds or more.
- Time: SNTP against time.nist.gov, time.google.com, time.cloudflare.com and pool.ntp.org. Each sync takes up to 6 samples per server, keeps the lowest-delay samples (within 1.5× the best delay + 5 ms, at most 10) and averages them, then keeps time on the phone's monotonic clock. The Sync button shows ± half the best round-trip delay, a bound on asymmetric-path error. Re-syncs every 10 min; works offline after a sync. Some mobile networks block NTP – try Wi-Fi.
- Earth orientation: UT1−UTC and polar motion from the USNO/IERS `finals2000A.daily` file (predictions are used up to 90 days ahead). Not included: celestial pole offsets (dX, dY, up to ~0.1″).
- Star data: names, magnitudes and constellation IDs from the Yale BSC; positions, proper motions and parallaxes replaced with Hipparcos astrometry (ICRS, epoch 1991.25 moved to J2000 with linear proper motion, about ±1 mas for most stars). Stars matched only to the BSC are about ±1″. The readout says which. A few binaries are off by more.
- Constellation stick figures for all 88 constellations were written from memory (Bayer/Flamsteed IDs); some faint southern figures may differ from IAU/Stellarium. They appear only for stars in the downloaded catalog.
- Deflection of the vertical: **DoV from NGS** queries the NGS GEOID18 geoid-height API at four points about 1′ (≈1.85 km) N/S/E/W of you and takes the geoid slope (ξ = −∂N/(M∂φ), η = −∂N/(N cos φ ∂λ)). DEFLEC18 itself has no API; this ignores its small plumb-line-curvature term, so check one location against the DEFLEC18 web page. Only for the continental USA and Puerto Rico/USVI. Elsewhere enter values by hand (default 0 = plumb line along the ellipsoid normal).
- Practical limits: the phone clock/network latency, your entered position, and weather dominate the error budget.

## Files
- `app/src/main/java/com/example/meridian/MainActivity.java` – the whole app
- `app/src/main/AndroidManifest.xml` – permissions: INTERNET, VIBRATE; portrait only
- `.github/workflows/build.yml` – GitHub Actions APK build
