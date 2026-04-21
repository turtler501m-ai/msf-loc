<template>
  <div class="guide-page">
    <GuideUnit
      name="Alert"
      title="공통 Alert"
      description="개발 공통으로 만드신 유틸 사용 (줄바꿈 \n 또는 br태그 사용가능)"
      caseTitle="import { showAlert, showConfirm } from '@/libs/utils/comp.utils'"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1" title="1. showAlert 함수 예제:">
          <!-- 1. 알림창 (Alert) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 알림메시지 -->
            <MsfButton @click="showAlert('알림 메세지입니다.')">알림메시지</MsfButton>
            <!-- 알림메시지 + 확인콜백함수 -->
            <MsfButton
              @click="
                showAlert('알림 메세지입니다.\n줄바꿈 입니다.', () => {
                  console.log('확인 버튼 클릭')
                })
              "
            >
              알림메시지 + 확인콜백함수
            </MsfButton>
            <!-- 알림메시지 + 확인콜백함수 + 하위메시지 -->
            <MsfButton
              @click="
                showAlert(
                  '알림 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '알림 하위 메세지입니다.<br/>줄바꿈 입니다.',
                )
              "
            >
              알림메시지 + 확인콜백함수 + 하위메시지
            </MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2" title="2. showConfirm 함수 예제:">
          <!-- 2. 확인창 (Confirm) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 확인메시지 -->
            <MsfButton @click="showConfirm('확인 메세지입니다.')">확인메시지</MsfButton>
            <!-- 확인메시지 + 확인콜백함수 -->
            <MsfButton
              @click="
                showConfirm('확인 메세지입니다.', () => {
                  console.log('확인 버튼 클릭')
                })
              "
              >확인메시지 + 확인콜백함수
            </MsfButton>
            <!-- 확인메시지 + 확인콜백함수 + 하위메시지 -->
            <MsfButton
              @click="
                showConfirm(
                  '확인 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '확인 하위 메세지입니다.',
                )
              "
            >
              확인메시지 + 확인콜백함수 + 하위메시지
            </MsfButton>
            <!-- 확인메시지 + 확인콜백함수 + 하위메시지 + 취소콜백함수 -->
            <MsfButton
              @click="
                showConfirm(
                  '확인 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '확인 하위 메세지입니다.',
                  () => {
                    console.log('취소 버튼 클릭')
                  },
                )
              "
            >
              확인메시지 + 확인콜백함수 + 하위메시지 + 취소콜백함수
            </MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './AlertGuide.vue?raw'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
// import { useAlert } from '@/hooks/useAlert'

// Alert 열기
// const { showAlert } = useAlert()

// 샘플 콜백함수
// const handleSampleAlert = () => {
//   console.log('처리가 완료되었습니다.')
// }
</script>
