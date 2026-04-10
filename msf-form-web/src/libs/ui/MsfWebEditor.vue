<template>
  <div class="msf-web-editor-container" :style="containerStyle">
    <div v-bind="$attrs" ref="msfWebEditorRef" class="msf-web-editor"></div>
  </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref, watch, computed } from 'vue'
import Quill from 'quill'
import hljs from 'highlight.js'
// Quill 2.0.x의 코어 스타일과 테마 스타일 로드
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'highlight.js/styles/atom-one-dark.css'

defineOptions({
  inheritAttrs: false,
})

const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  // 높이 지정
  height: {
    type: [String, Number],
    default: '300px',
  },
})

const emit = defineEmits(['update:modelValue'])

// 에디터 인스턴스에 접근하기 위한 참조 변수
const msfWebEditorRef = ref(null)
let quillInstance = null

// (옵션) 모든 언어를 불러오면 무거울 수 있으므로, 프로젝트에서 주로 다루는 언어만 지정할 수도 있습니다.
hljs.configure({
  languages: ['html'],
})

// 💡 1. 커스텀 이미지 핸들러 함수 정의
const imageHandler = () => {
  const input = document.createElement('input')
  input.setAttribute('type', 'file')
  input.setAttribute('accept', 'image/*')
  input.click()

  input.onchange = async () => {
    const file = input.files[0]
    console.log('input:', input)
    if (!file) return

    try {
      // ==========================================================
      // 🚀 실무 적용 시: 실제 서버 API로 이미지를 전송하는 로직
      // ==========================================================
      // const formData = new FormData()
      // formData.append('image', file)
      // const response = await axios.post('/api/upload/image', formData)
      // const imageUrl = response.data.url

      // 테스트를 위한 브라우저 임시 URL 생성
      const imageUrl = URL.createObjectURL(file)

      // 💡 vue-quill과 달리 복잡한 접근 없이 quillInstance를 바로 사용합니다!
      // getSelection(true)를 주면 에디터에 포커스가 없어도 마지막 커서 위치를 찾아냅니다.
      const range = quillInstance.getSelection(true)
      const index = range ? range.index : quillInstance.getLength()

      // 현재 커서 위치에 이미지 태그 삽입
      quillInstance.insertEmbed(index, 'image', imageUrl)

      // 이미지 삽입 후 커서를 이미지 바로 뒤로 이동
      quillInstance.setSelection(index + 1)
    } catch (error) {
      console.error('이미지 업로드 중 오류 발생:', error)
      alert('이미지 업로드에 실패했습니다.')
    }
  }
}

const msfWebEditorOptions = {
  placeholder: '내용을 입력하세요',
  contentType: 'html',
  theme: 'snow',
  modules: {
    toolbar: {
      container: [
        ['bold', 'italic', 'underline', 'strike'], // <strong>, <em>, <u>, <s>
        [{ size: ['small', false, 'large', 'huge'] }], //class 제어 - html로 되도록 확인
        [{ color: [] }, { background: [] }], //style="color: rgb(230, 0, 0);", style="background-color: rgb(230, 0, 0);"
        [{ align: [] }], // class
        [{ list: 'ordered' }, { list: 'bullet' }],
        ['link', 'image', 'video'],
      ],
      // // 커스텀 핸들러를 툴바 버튼에 매핑합니다
      // handlers: {
      //   image: imageHandler,
      // },
    },
    syntax: {
      hljs: hljs,
    },
  },
}

// 부모 컴포넌트에서 데이터가 강제로 변경되었을 때 에디터 화면 동기화
watch(
  () => props.modelValue,
  (newVal) => {
    if (quillInstance) {
      const currentHtml = quillInstance.root.innerHTML
      const isSame = currentHtml === newVal || (currentHtml === '<p><br></p>' && newVal === '')

      // 커서가 튀는 현상을 막기 위해 내용이 진짜 다를 때만 업데이트
      if (!isSame) {
        // 현재 커서 위치를 기억
        const selection = quillInstance.getSelection()

        quillInstance.clipboard.dangerouslyPasteHTML(newVal || '')

        // 커서 위치 복원
        if (selection) {
          quillInstance.setSelection(selection.index, selection.length)
        }
      }
    }
  },
)

onMounted(() => {
  // 1. Quill 2.0 인스턴스 초기화
  quillInstance = new Quill(msfWebEditorRef.value, msfWebEditorOptions)

  // 2. 초기 데이터 주입
  if (props.modelValue) {
    quillInstance.clipboard.dangerouslyPasteHTML(props.modelValue)
  }

  // 3. 타이핑 이벤트 감지 및 부모로 HTML 전달
  quillInstance.on('text-change', () => {
    // Quill 2.0에서 HTML을 추출하는 표준 방식
    let html = quillInstance.root.innerHTML

    // 에디터가 완전히 비워졌을 때 남는 쓰레기 태그 정리
    if (html === '<p><br></p>') html = ''

    emit('update:modelValue', html)
  })
})

// 메모리 누수 방지
onBeforeUnmount(() => {
  quillInstance = null
})

// 부모 래퍼의 높이를 props로 제어
const containerStyle = computed(() => ({
  height: props.height,
}))
</script>

<style lang="scss" scoped>
.msf-web-editor-container {
  // form 보더 컬러 맞춤
  :deep(.ql-toolbar.ql-snow),
  :deep(.ql-container.ql-snow) {
    border-color: var(--color-gray-400);
  }
  /* Quill의 내부 스크롤 영역이 전체 높이를 채우도록 강제 설정 */
  :deep(.ql-container) {
    height: calc(100% - 42px) !important; /* 툴바 높이(약 42px)를 뺀 나머지 전체 */
    display: flex;
    flex-direction: column;
  }
  :deep(.ql-editor) {
    flex: 1;
    overflow-y: auto;
  }
}
</style>
