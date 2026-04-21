<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="공지사항"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfBox margin="0">
      <MsfStack vertical>
        <MsfStack type="field" class="ut-w100p">
          <MsfInput v-model="formData.searchField" class="ut-w-347" placeholder="검색어 입력" />
          <MsfSelect
            title="유형"
            v-model="formData.type"
            :options="[
              { label: '유형1', value: 'type1' },
              { label: '유형2', value: 'type2' },
            ]"
            placeholder="유형"
            class="ut-flex-1"
          />
          <MsfButton variant="primary" noMinWidth @click="onClickSearchPaging">검색</MsfButton>
        </MsfStack>
        <MsfStack type="field" class="ut-w-347">
          <MsfDateRange
            v-model:from="rangeDatePickerValue.start"
            v-model:to="rangeDatePickerValue.end"
            class="ut-w100p"
          />
        </MsfStack>
      </MsfStack>
    </MsfBox>
    <!-- 아코디언 -->
    <MsfAccordion :data="faqData" isMultiple>
      <template #label="{ item }">
        <div class="custom-label">
          <span class="text">{{ item.label }}</span>
          <span v-if="item.isNew" class="flag-new">
            <MsfFlag data="NEW" color="accent2" size="small" />
          </span>
          <span v-if="item.status === 'done'" class="flag-done">
            <MsfFlag data="답변완료" color="accent2" size="small" />
          </span>
          <span v-if="item.status === 'waiting'" class="flag-done">
            <MsfFlag data="답변대기" color="accent2" size="small" variant="outlined" />
          </span>
        </div>
      </template>
    </MsfAccordion>
    <!-- // 아코디언 -->

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, reactive } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

// 퍼블샘플용
const formData = reactive({
  searchField: '', //검색어입력 필드
  gubun: '', //신청서 구분
})
const rangeDatePickerValue = ref({ start: '', end: '' }) //기간

// 아코디언
const faqData = [
  {
    label: '개인정보처리방침 변경 안내',
    field: 'notice',
    content: '영업일 기준 2~3일 정도 소요됩니다.',
    isNew: true,
  },
  {
    label: '환불 규정이 어떻게 되나요?',
    content: '수령 후 7일 이내에 신청 가능합니다.',
    status: 'done',
  },
  {
    label: '아이디를 잊어버렸어요.',
    content: '로그인 화면 하단의 [아이디 찾기]를 이용해 주세요.',
    status: 'waiting',
  },
]
const faqData2 = [
  {
    label: '배송 관련 자주 묻는 질문',
    isNew: true,
    // 상세 리스트가 들어가는 경우
    details: [{ q: '배송은 얼마나 걸리나요?', a: '영업일 기준 2~3일 정도 소요됩니다.' }],
    content: '수령 후 7일 이내에 신청 가능합니다.123123',
  },
  {
    label: '환불 규정이 어떻게 되나요?',
    // 리스트 없이 일반 문자열만 있는 경우
    content: '수령 후 7일 이내에 신청 가능합니다.',
  },
]
</script>

<style lang="scss" scoped></style>
