#!/bin/bash

set -e

APP_DIR=~/everybox
cd "$APP_DIR"

if ! command -v docker &> /dev/null; then
    sudo apt update -y
    sudo apt install -y docker.io
    sudo systemctl start docker
    sudo systemctl enable docker
fi
echo "[1] docker 설치 완료"

if ! command -v docker-compose &> /dev/null; then
  sudo apt update -y
  sudo apt install -y docker-compose
fi
echo "[2] docker-compose 설치 완료"

echo "컨테이너 재시작"
sudo docker-compose down
sudo docker-compose pull
sudo docker-compose up -d

echo "[✅ 배포 완료]"