<template>
  <div class="guide-page">
    <GuideUnit
      name="focusField"
      title="focusField (focus.utils)"
      description="DOM에서 id, name, data-focus-key, CSS Selector 으로 지정한 요소를 타겟으로 포커스를 주고 스크롤을 이동시킴"
      caseTitle="import { focusField } from '@/libs/utils/focus.utils''"
    >
      <template #cases>
        <GuideSourceBox
          :source="selfSource"
          id="ex1"
          title="1. DOM에 지정한 요소의 id, name, data-focus-key, CSS Selector으로 지정한 요소를 타겟으로 포커스 후 스크롤 이동"
        >
          <MsfButtonGroup align="left">
            <!-- 1. focusField(id)  -->
            <MsfButton
              @click="
                showAlert('inp-id 요소로 포커스', () => {
                  focusField('inp-id')
                })
              "
              >focusField(id)
            </MsfButton>
            <!-- 2. focusField(name)  -->
            <MsfButton
              @click="
                showAlert('inp-name 요소로 포커스', () => {
                  focusField('inp-name')
                })
              "
              >focusField(name)
            </MsfButton>
            <!-- 3. focusField(data-focus-key)  -->
            <MsfButton
              @click="
                showAlert('data-focus-key 요소로 포커스', () => {
                  focusField('inp-focus-key')
                })
              "
              >focusField(data-focus-key)
            </MsfButton>
            <!-- 4. focusField(CSS-selector)  -->
            <MsfButton
              @click="
                showAlert('inp-selector 요소로 포커스', () => {
                  focusField('.inp-selector')
                })
              "
              >focusField(CSS-selector)
            </MsfButton>
          </MsfButtonGroup>

          <!-- 1. id="inp-id" 로 설정된 타겟 -->
          <span class="space-block">1. id="inp-id" 로 설정된 타겟</span>
          <MsfInput id="inp-id" placeholder="id='inp-id'" />

          <!-- 2. name="inp-name" 로 설정된 타겟 -->
          <span class="space-block">2. name="inp-name" 로 설정된 타겟</span>
          <MsfInput name="inp-name" placeholder="name='inp-name'" />

          <!-- 3. data-focus-key="inp-data-focus-key" 로 설정된 타겟 -->
          <span class="space-block">3. data-focus-key="inp-focus-key" 로 설정된 타겟</span>
          <MsfInput data-focus-key="inp-focus-key" placeholder="data-focus-key='inp-focus-key'" />

          <!-- 4. class="inp-selector" 로 설정된 타겟 -->
          <span class="space-block">4. class="inp-selector" 로 설정된 타겟</span>
          <MsfInput class="inp-selector" placeholder="class='inp-selector'" />
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './FocusFieldGuide.vue?raw'
import { showAlert } from '@/libs/utils/comp.utils'
import { focusField } from '@/libs/utils/focus.utils' // 입력필드 포커스 유틸
</script>

<style>
.space-block {
  display: block;
  margin-block: 120px 10px;
  font-size: 18px;
  font-weight: 500;
}
</style>
