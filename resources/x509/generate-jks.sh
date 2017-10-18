#!/bin/bash

rm lab-insurance.jks
rm lab-insurance.cer


keytool -genkey \
  -storetype JKS \
  -storepass changeit \
  -keypass changeit \
  -keystore lab-insurance.jks \
  -keyalg RSA \
  -keysize 2048 \
  -sigalg SHA256withRSA \
  -alias lab-insurance \
  -dname "cn=lab-insurance, ou=labcabrera, o=labcabrera, c=ES"

keytool -export \
  -file lab-insurance.cer \
  -keystore lab-insurance.jks \
  -storepass changeit \
  -alias lab-insurance
