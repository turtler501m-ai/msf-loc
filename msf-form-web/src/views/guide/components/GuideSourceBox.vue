<template>
  <div v-if="props.title || props.description" class="info-area">
    <h4 v-if="props.title" class="source-title">{{ props.title }}</h4>
    <p v-if="props.description" class="source-desc">{{ props.description }}</p>
  </div>
  <div class="source-box">
    <div class="preview-area">
      <slot></slot>
    </div>

    <div class="code-area">
      <div class="code-header">
        <span>SOURCE CODE</span>
        <button class="copy-btn" @click="handleCopy">
          {{ copied ? 'Copied!' : 'Copy' }}
        </button>
      </div>
      <pre><code v-html="highlightedCode"></code></pre>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, getCurrentInstance } from 'vue'

const props = defineProps({
  source: { type: String, required: true },
  id: { type: String, default: '' },
  // 소스박스 타이틀 설정시
  title: { type: String, default: '' },
  description: { type: String, default: '' },
})

// 1. 현재 컴포넌트의 태그 이름을 자동으로 가져오기
const instance = getCurrentInstance()
const tagName = instance?.type.__name || 'GuideSourceBox'

const copied = ref(false)

const handleCopy = () => {
  navigator.clipboard.writeText(extractedCode.value).then(() => {
    copied.value = true
    setTimeout(() => (copied.value = false), 2000)
  })
}

const extractedCode = computed(() => {
  if (!props.source) return ''

  /**
   * 2. tagName 변수를 사용하여 정규식 생성
   * <GuideSourceBox>든 <SourceBox>든 파일명에 따라 자동으로 대응
   */
  const pattern = props.id
    ? `<${tagName}[^>]*id=["']${props.id}["'][^>]*>([\\s\\S]*?)<\\/${tagName}>`
    : `<${tagName}[^>]*>([\\s\\S]*?)<\\/${tagName}>`

  const regex = new RegExp(pattern)
  const match = props.source.match(regex)
  const content = match ? match[1] : ''

  const lines = content.split('\n').filter((line, i, arr) => {
    if ((i === 0 || i === arr.length - 1) && !line.trim()) return false
    return true
  })

  const minIndent = lines.reduce((min, line) => {
    const match = line.match(/^(\s*)\S/)
    return match ? Math.min(min, match[1].length) : min
  }, Infinity)

  return lines
    .map((line) => line.slice(minIndent === Infinity ? 0 : minIndent))
    .join('\n')
    .trim()
})

const highlightedCode = computed(() => {
  const code = extractedCode.value
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')

  return code.replace(/(&lt;!--[\s\S]*?--&gt;)/g, '<span class="code-comment">$1</span>')
})
</script>

<style scoped>
.source-box {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
  margin-block: 20px 40px;
}
.preview-area {
  padding: 24px;
  background: #fff;
}
.code-area {
  background: #1e1e1e;
  padding: 16px;
  border-top: 1px solid #e5e7eb;
}
.code-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #6b7280;
  font-size: 11px;
  font-weight: bold;
  margin-bottom: 8px;
}

.copy-btn {
  background: #374151;
  border: none;
  color: #d1d5db;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 10px;
  cursor: pointer;
}

pre {
  margin: 0;
  white-space: pre-wrap;
  word-break: break-all;
  color: #d1d5db;
  font-size: 13px;
  font-family: 'Fira Code', monospace;
  line-height: 1.6;
}

/* 주석 전용 스타일 (소스코드와 구분을 위함) */
:deep(.code-comment) {
  color: #7fb79b; /* 차분한 초록색 혹은 #666 회색 추천 */
}

/* 타이틀,설명 스타일 */
/* .info-area {
  padding: 16px 24px;
  border-bottom: 1px dashed #e5e7eb;
  background-color: #fafafa;
} */
.source-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}
.source-desc {
  margin: 8px 0 0;
  font-size: 14px;
  color: #6b7280;
  line-height: 1.5;
}
</style>
