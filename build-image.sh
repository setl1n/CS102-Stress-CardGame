native-image \
  -H:+UnlockExperimentalVMOptions \
  --initialize-at-build-time=java.desktop \
  --initialize-at-build-time=com.sun.media.sound \
  --initialize-at-build-time=javax.sound.sampled,com.sun.media.sound \
  --initialize-at-run-time=com.sun.media.sound.DirectAudioDevice \
  --initialize-at-run-time=com.sun.media.sound.PortMixer \
  --initialize-at-run-time=com.sun.media.sound.MixerThread \
  --initialize-at-run-time=javax.sound.sampled.AudioSystem \
  --initialize-at-run-time=com.sun.media.sound.JSSecurityManager \
  -H:+AllowIncompleteClasspath \
  -H:EnableURLProtocols=resource,file \
  -H:+ReportExceptionStackTraces \
  --enable-monitoring \
  -Djava.awt.headless=false \
  -H:ConfigurationFileDirectories=./META-INF/native-image/ \
  -H:IncludeResources=".*/assets/.*" \
  -H:IncludeResources=".*\\.properties" \
  -H:IncludeResources="META-INF/services/.*" \
  --enable-url-protocols=resource,file \
  -H:+PrintClassInitialization \
  --enable-all-security-services \
  -jar target/stress-cardgame-1.0.jar stress
# native-image  -H:+UnlockExperimentalVMOptions -H:Name=StressCardGame -H:ConfigurationFileDirectories=./META-INF/native-image/ -H:IncludeResources="./assets/*" -jar target/cs102-cardgame-1.0-SNAPSHOT.jar stress

#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.AudioFileReader:serviceloader \
#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.FormatConversionProvider:serviceloader \
#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.MixerProvider:serviceloader \
#   -H:+TraceServiceLoaderFeature \
#   -Djava.home="." \