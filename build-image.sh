native-image \
  -H:+UnlockExperimentalVMOptions \
  --initialize-at-build-time=com.sun.media.sound \
  --initialize-at-build-time=javax.sound.sampled.AudioFormat \
  --initialize-at-build-time=com.sun.media.sound.JSSecurityManager \
  --initialize-at-run-time=com.sun.media.sound.DirectAudioDevice \
  --initialize-at-run-time=javax.sound.sampled.AudioSystem \
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
  -jar target/cs102-cardgame-1.0-SNAPSHOT.jar stress
# native-image  -H:+UnlockExperimentalVMOptions -H:Name=StressCardGame -H:ConfigurationFileDirectories=./META-INF/native-image/ -H:IncludeResources="./assets/*" -jar target/cs102-cardgame-1.0-SNAPSHOT.jar stress

#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.AudioFileReader:serviceloader \
#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.FormatConversionProvider:serviceloader \
#   -H:ServiceLoaderFeatureConfiguration=javax.sound.sampled.spi.MixerProvider:serviceloader \
#   -H:+TraceServiceLoaderFeature \
#   --initialize-at-run-time=com.sun.media.sound.JSSecurityManager \
#   -Djava.home="." \