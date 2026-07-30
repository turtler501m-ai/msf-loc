<!-- 설정 -->
<template>
  <MsfTitleBar title="설정" />
  <!-- 로그인 설정 -->
  <MsfTitleArea title="로그인 설정" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="지문 로그인 설정">
      <MsfSwitch v-model="formData.isFingerLogin" showInnerLabel />
    </MsfFormGroup>
    <MsfFormGroup label="Face ID<br/>로그인 설정">
      <MsfSwitch v-model="formData.isFaceId" showInnerLabel />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 로그인 설정 -->
  <!-- App 정보 -->
  <MsfTitleArea title="App 정보" />
  <MsfBox variant="outline">
    <div class="app-version">
      <img src="@/assets/images/appIcon.svg" alt="kt m mobile 앱 아이콘" class="app-img" />
      <div class="app-version-msg">
        <em class="app-version-txt">APP 버전 <span class="version">v 1.0.1</span></em>
        <span class="app-version-desc">최신 버전 업데이트를 해주시기 바랍니다.</span>
      </div>
      <MsfButtonGroup align="center" margin="2">
        <MsfButton variant="subtle">업데이트</MsfButton>
      </MsfButtonGroup>
    </div>
  </MsfBox>
  <!-- // App 정보 -->
</template>

<script setup>
import { reactive } from 'vue'

// 퍼블 샘플
const formData = reactive({
  isFingerLogin: true, //지문 로그인 설정
  isFaceId: false, //페이스ID 로그인 설정
})
</script>

<style lang="scss" scoped></style>
