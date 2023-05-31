# Social Scheduler
Bilkent CS First Year Android App Project

## Contributors:
1-Ahmet Deniz Gelir  
2-İbrahim Karaca  
3-Deniz Öztürk  
4-Mehmet Efe Sak  


dependencies:
    implementation "androidx.cardview:cardview:1.0.0"  
    implementation 'androidx.appcompat:appcompat:1.6.1'  
    implementation 'com.google.android.material:material:1.8.0'  
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'  
    implementation 'androidx.navigation:navigation-fragment:2.4.1'  
    implementation 'androidx.navigation:navigation-ui:2.4.1'  
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'  
    implementation 'androidx.activity:activity-compose:1.5.1'  
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation 'com.google.firebase:firebase-auth-ktx:22.0.0'
    implementation 'com.google.firebase:firebase-firestore-ktx:24.6.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation platform('com.google.firebase:firebase-bom:31.5.0')
    implementation 'com.google.firebase:firebase-firestore'
    implementation 'com.google.firebase:firebase-auth'
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    debugImplementation 'androidx.compose.ui:ui-tooling'
    debugImplementation 'androidx.compose.ui:ui-test-manifest'
    
    
do not forget to connect to the firebase from the tools bar of android studio.
json file is already included so all you need to do just click connect to Firestore and Authentication.
Make sure you sync the gradle build first. Then you are ready to go...
