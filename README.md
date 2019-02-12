# AndroidEqualizer   [![](https://jitpack.io/v/mosamabinomar/AndroidEqualizer.svg)](https://jitpack.io/#mosamabinomar/AndroidEqualizer)

Android Equalizer View that can also manage the audio track frequencies 

Add Equalizer in your Android app
<div>
  <img src="https://raw.githubusercontent.com/mosamabinomar/AndroidEqualizer/master/screenshots/Screenshot_1522935541.png" width="300" height="500"/>
  <img src="https://raw.githubusercontent.com/mosamabinomar/AndroidEqualizer/master/screenshots/Screenshot_1522962328.png" width="300" height="500"/>
  <br>
  <img src="https://raw.githubusercontent.com/mosamabinomar/AndroidEqualizer/master/screenshots/Screenshot_1522962331.png" width="300" height="500"/>
</div>

## How To Use
### STEP 1
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
     implementation 'com.github.mosamabinomar:AndroidEqualizer:1.0'
}
```
for **AndroidX**

```gradle
dependencies {
     implementation 'com.github.mosamabinomar:AndroidEqualizer:2.0'
}
```
### STEP 2
Create a frame in your layout file. 
```    
<FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        android:id="@+id/eqFrame"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
        
```
### STEP 3
In your Activity class
```
 int sessionId = mediaPlayer.getAudioSessionId();
        mediaPlayer.setLooping(true);
        EqualizerFragment equalizerFragment = EqualizerFragment.newBuilder()
                .setAccentColor(Color.parseColor("#4caf50"))
                .setAudioSessionId(sessionId)
                .build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.eqFrame, equalizerFragment)
                .commit();
```
**This work is mostly borrowed from https://github.com/harjot-oberai/MusicDNA**
