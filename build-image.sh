native-image \
  -H:+UnlockExperimentalVMOptions \
  --initialize-at-build-time=com.sun.media.sound \
  -H:+AddAllCharsets \
  -H:EnableURLProtocols=resource \
  -H:+ReportExceptionStackTraces \
  --enable-monitoring \
  --initialize-at-run-time=com.sun.media.sound.DirectAudioDevice \
  -H:ServiceLoaderFeatureExcludeServices=javax.sound.sampled.spi.AudioFileReader \
  --initialize-at-run-time=javax.sound.sampled.AudioSystem \
  -Djava.awt.headless=false \
  -H:ConfigurationFileDirectories=./META-INF/native-image/ -H:IncludeResources="./assets/*" \
  -jar target/cs102-cardgame-1.0-SNAPSHOT.jar stress
# native-image  -H:+UnlockExperimentalVMOptions -H:Name=StressCardGame -H:ConfigurationFileDirectories=./META-INF/native-image/ -H:IncludeResources="./assets/*" -jar target/cs102-cardgame-1.0-SNAPSHOT.jar stress
