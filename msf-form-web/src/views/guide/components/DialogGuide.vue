<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfDialog"
      title="MsfDialog"
      description="MsfDialog 코드를 페이지 내에서 선언하거나 파일로 정의하여 사용합니다."
      caseTitle="사용예시"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1">
          <MsfButton @click="isModalOpen = true">기본 모달 열기</MsfButton>
          <!-- 기본팝업의 형태 -->
          <MsfDialog
            :isOpen="isModalOpen"
            title="팝업 타이틀"
            showClose
            @open="handleModalOpen"
            @close="handleModalClose"
          >
            <template #navBar>
              <MsfButtonGroup>
                <MsfButton>버튼1</MsfButton>
                <MsfButton variant="primary">버튼2</MsfButton>
              </MsfButtonGroup>
            </template>
            <p>자유로운 내용</p>
            <template #footer>하단 고정영역</template>
          </MsfDialog>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <MsfButton @click="isDialogSampleOpen = true">DialogSample.vue 파일로 열기</MsfButton>
          <!-- Dialog를 파일로 생성했을 경우 (DialogSample.vue) -->
          <DialogSample
            v-model="isDialogSampleOpen"
            @open="console.log('열릴때 이벤트')"
            @close="console.log('닫힐때 이벤트')"
            @confirm="console.log('확인 이벤트')"
          />
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex3">
          <MsfButton @click="isPopinPopOpen = true">팝업 중첩</MsfButton>
          <!-- Dialog안에 Dialog -->
          <MsfDialog
            :isOpen="isPopinPopOpen"
            title="팝업 중첩 타이틀"
            showClose
            @close="isPopinPopOpen = false"
          >
            <template #navBar>
              <MsfButtonGroup>
                <MsfButton>버튼1</MsfButton>
                <MsfButton variant="primary">버튼2</MsfButton>
              </MsfButtonGroup>
            </template>
            <div class="">
              <MsfButton @click="isInnerModal = true">팝업또연다</MsfButton>
              <MsfButton @click="showAlert('처리가 완료되었습니다.')"> 알럿 </MsfButton>
            </div>
            <template #footer>하단 고정영역</template>
            <!-- 내부 Dialog -->
            <MsfDialog :isOpen="isInnerModal" title="이것은 내부팝업" @close="isInnerModal = false">
              <template #navBar>
                <MsfButtonGroup>
                  <MsfButton>버튼1</MsfButton>
                  <MsfButton variant="primary">버튼2</MsfButton>
                </MsfButtonGroup>
              </template>
              <p>이것은 또 다른 팝업</p>
              <template #footer>하단 고정영역</template>
            </MsfDialog>
          </MsfDialog>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './DialogGuide.vue?raw'
// import { useAlert } from '@/hooks/useAlert'
import { showAlert } from '@/libs/utils/comp.utils'

// Alert 열기
// const { showAlert } = useAlert()

// Dialog 파일 열기
import DialogSample from '@/views/guide/components/DialogSample.vue'
const isDialogSampleOpen = ref(false)
const isPopinPopOpen = ref(false)
const isInnerModal = ref(false) // 팝업내부 팝업

// Dialog 열기
const isModalOpen = ref(false)
// 모달이 열릴 때 실행될 함수 선언
const handleModalOpen = () => {
  console.log('modal 열림! 이벤트 선언하세요!')
}
// 모달이 닫힐 때 실행될 함수 선언
const handleModalClose = () => {
  isModalOpen.value = false
  console.log('modal 닫힘! 이벤트 선언하세요!')
}
</script>
