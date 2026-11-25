## 1. 기존 원격 저장소 연결 확인
git remote -v
## 2. 원격 저장소 주소 연결
git remote add origin https://github.com/bearonyeon/PT-Manager.git
## 3. 원격 저장소의 최신 브랜치 목록 정보 업데이트
git fetch origin
## 4. 해당 브랜치만 다운로드
git clone -b feature/mvc-setup
