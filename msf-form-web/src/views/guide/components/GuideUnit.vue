<template>
  <div class="guide-unit">
    <header class="sb-header">
      <h1 v-if="title" class="sb-title" v-html="title"></h1>
      <p v-if="description" class="sb-desc" v-html="description"></p>
    </header>

    <section v-if="Object.keys(state).length > 0 || $slots.default" class="sb-canvas">
      <div class="canvas-header">
        <span class="section-label">PREVIEW</span>
      </div>
      <div class="canvas-preview">
        <slot :props="state"></slot>
      </div>
      <div class="canvas-footer">
        <button class="show-code-btn" @click="isCodeOpen = !isCodeOpen">
          {{ isCodeOpen ? 'Hide code' : 'Show code' }}
        </button>
      </div>
      <div v-show="isCodeOpen" class="sb-code-block">
        <div class="code-container">
          <pre><code>{{ generatedCode }}</code></pre>
          <button class="copy-btn" @click="copyToClipboard(generatedCode)">Copy</button>
        </div>
      </div>
    </section>

    <section v-if="config && Object.keys(config).length > 0" class="sb-controls-section">
      <table class="sb-table">
        <thead>
          <tr>
            <th width="18%">Name</th>
            <th width="32%">Description</th>
            <th width="12%">Default</th>
            <th width="38%">Control</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(conf, key) in config" :key="key">
            <td class="col-name">
              <span class="prop-name">{{ key }}</span>
            </td>
            <td class="col-desc">
              <div v-if="conf?.description" class="prop-desc-text">{{ conf.description }}</div>
              <div class="type-tag">{{ getPropType(state[key]) }}</div>
            </td>
            <td class="col-default">
              <code class="default-val">{{ getDisplayDefault(key) }}</code>
            </td>
            <td class="col-control">
              <select
                v-if="Array.isArray(conf) || conf?.options"
                v-model="state[key]"
                class="sb-input"
              >
                <option :value="undefined">선택</option>
                <option
                  v-for="opt in Array.isArray(conf) ? conf : conf.options"
                  :key="opt"
                  :value="opt"
                >
                  {{ opt }}
                </option>
              </select>
              <div v-else-if="typeof state[key] === 'boolean'" class="switch-container">
                <label class="switch">
                  <input type="checkbox" v-model="state[key]" />
                  <span class="slider round"></span>
                </label>
                <span class="switch-text">{{ state[key] ? 'True' : 'False' }}</span>
              </div>
              <textarea
                v-else-if="typeof state[key] === 'object' && state[key] !== null"
                :value="JSON.stringify(state[key], null, 2)"
                @input="handleObjectInput(key, $event.target.value)"
                rows="5"
                class="sb-textarea"
                spellcheck="false"
              ></textarea>
              <input v-else type="text" v-model="state[key]" class="sb-input" />
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <section v-if="$slots.cases" class="sb-cases-section">
      <div class="cases-header">
        <h2 class="cases-title">{{ caseTitle || 'Examples & Cases' }}</h2>
        <p v-if="caseDescription" class="cases-desc" v-html="caseDescription"></p>
      </div>
      <div class="cases-content">
        <slot name="cases"></slot>
      </div>
    </section>

    <section v-if="stories?.length" class="sb-stories-section">
      <!-- <div class="cases-header">
        <h2 class="cases-title">{{ caseTitle || 'Stories' }}</h2>
        <p v-if="caseDescription" class="cases-desc" v-html="caseDescription"></p>
      </div> -->
      <div class="stories-container">
        <div v-for="(story, idx) in stories" :key="idx" class="story-item-group">
          <div class="story-info">
            <h3 class="story-name">{{ story.name }}</h3>
            <p v-if="story.description" class="story-desc">{{ story.description }}</p>
          </div>

          <div class="sb-canvas">
            <div class="canvas-preview">
              <slot name="story" :args="story.args"></slot>
            </div>
            <div class="canvas-footer">
              <button class="show-code-btn" @click="toggleStoryCode(idx)">
                {{ openStories[idx] ? 'Hide code' : 'Show code' }}
              </button>
            </div>
            <div v-show="openStories[idx]" class="sb-code-block">
              <div class="code-container">
                <code>{{ generateStoryCode(story.args) }}</code>
                <button class="copy-btn" @click="copyToClipboard(generateStoryCode(story.args))">
                  Copy
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { reactive, computed, ref, watch } from 'vue'

const props = defineProps({
  name: String,
  title: String,
  description: String,
  config: Object,
  initialState: Object,
  caseTitle: String,
  caseDescription: String,
  exampleCode: String, // 추가
  stories: {
    type: Array,
    default: () => [],
  },
})

const state = reactive({ ...props.initialState })
const isCodeOpen = ref(false)
const isExCodeOpen = ref(false) // 추가

const openStories = ref([])

watch(
  () => props.stories,
  (newStories) => {
    openStories.value = new Array(newStories.length).fill(false)
  },
  { immediate: true },
)

const toggleStoryCode = (index) => {
  openStories.value[index] = !openStories.value[index]
}

const getPropType = (val) => {
  if (val === undefined) return 'any'
  if (Array.isArray(val)) return 'array'
  if (val === null) return 'object'
  return typeof val
}

const getDisplayDefault = (key) => {
  const conf = props.config[key]
  if (conf && typeof conf === 'object' && !Array.isArray(conf) && 'default' in conf) {
    const d = conf.default
    return d === '' || d === null || d === undefined ? '-' : String(d)
  }
  return '-'
}

const handleObjectInput = (key, value) => {
  try {
    state[key] = JSON.parse(value)
  } catch (e) {}
}

const generatedCode = computed(() => {
  let hasObject = false
  const activeProps = Object.entries(state)
    .filter(([key]) => !['slotText', 'modelValue', 'model'].includes(key))
    .filter(([_, value]) => value !== null && value !== undefined && value !== '')
    .map(([key, value]) => {
      if (typeof value === 'boolean') return value ? key : null
      if (typeof value === 'object') {
        hasObject = true
        return `:${key}='${JSON.stringify(value, null, 2).replace(/\n/g, '\n  ')}'`
      }
      return `${key === 'error' ? ':' : ''}${key}="${value}"`
    })
    .filter(Boolean)

  const inner = state.slotText ? state.slotText.trim() : ''
  const isMulti = activeProps.length >= 2 || hasObject
  const propsStr = isMulti
    ? `\n  ${activeProps.join('\n  ')}`
    : activeProps[0]
      ? ` ${activeProps[0]}`
      : ''

  if (inner) {
    return `<${props.name}${propsStr}${isMulti ? '\n>' : '>'}\n  ${inner}\n</${props.name}>`
  }
  return `<${props.name}${propsStr}${isMulti ? '\n/>' : ' />'}`
})

const generateStoryCode = (args) => {
  if (!args) return ''
  const activeProps = Object.entries(args)
    .filter(([key]) => !['slotText', 'modelValue', 'model'].includes(key))
    .map(([key, value]) => {
      if (typeof value === 'boolean') return value ? key : null
      if (typeof value === 'object') return `:${key}='${JSON.stringify(value)}'`
      return `${key}="${value}"`
    })
    .filter(Boolean)

  const propsStr = activeProps.length > 0 ? ' ' + activeProps.join(' ') : ''
  const content = args.slotText || ''

  return content
    ? `<${props.name}${propsStr}>\n  ${content}\n</${props.name}>`
    : `<${props.name}${propsStr} />`
}

const copyToClipboard = (text) => {
  navigator.clipboard.writeText(text).then(() => alert('Copied!'))
}
</script>

<style lang="scss" scoped>
/* 기존 스타일 100% 동일하게 유지 */
.story-item {
  border: 1px solid #e5e5e5;
  border-radius: 6px;
  background: white;
  overflow: hidden;
  margin-bottom: 40px;
}

.story-preview {
  padding: 40px 20px;
  background: #ffffff !important;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100px;
}

.story-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px 16px;
  border-top: 1px solid #f0f0f0;
  background: white;
}

.story-code-block {
  border-top: 1px solid #333;
  margin-top: 0;
}

.guide-unit {
  max-width: 960px;
  margin: 0 auto;
  padding: 30px 20px 120px;
  color: #333;
}
.sb-header {
  margin-bottom: 32px;
}
.sb-title {
  font-size: 28px;
  font-weight: bold;
  color: #111;
  margin-bottom: 8px;
}
.sb-desc {
  font-size: 15px;
  color: #666;
}

.sb-canvas {
  border: 1px solid #e5e5e5;
  border-radius: 6px;
  margin-bottom: 32px;
  background: white;
}
.canvas-header {
  padding: 10px 16px;
  background: #f6f8fa;
  border-bottom: 1px solid #e5e5e5;
}
.section-label {
  font-size: 11px;
  font-weight: 800;
  color: #999;
  letter-spacing: 0.1em;
}
.canvas-preview {
  padding: 30px 20px;
  background: #fff;
  min-height: 100px;
}
.canvas-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px 16px;
  border-top: 1px solid #f0f0f0;
}

.show-code-btn {
  background: white;
  border: 1px solid #dcdcdc;
  padding: 5px 12px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 700;
  cursor: pointer;
  color: #666;
  &:hover {
    background: #f9f9f9;
  }
}

.sb-code-block {
  background: #1e1e1e;
}
.code-container {
  position: relative;
  padding: 20px;
}
.sb-code-block code {
  font-size: 13px;
  line-height: 1.6;
  color: #96cbfe;
  white-space: pre-wrap;
}
.copy-btn {
  position: absolute;
  bottom: 12px;
  right: 12px;
  background: #2a2a2a;
  border: 1px solid #444;
  color: #fff;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 11px;
  font-weight: 600;
  cursor: pointer;
}

.sb-controls-section {
  border: 1px solid #e5e5e5;
  border-radius: 6px;
  overflow: hidden;
}
.sb-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  table-layout: fixed;
}
.sb-table th {
  padding: 12px 16px;
  font-size: 11px;
  color: #999;
  text-transform: uppercase;
  background: #fdfdfd;
  border-bottom: 1px solid #e5e5e5;
}
.sb-table td {
  padding: 16px;
  vertical-align: top;
  border-bottom: 1px solid #eee;
  word-wrap: break-word;
}
.prop-name {
  font-weight: 700;
  font-size: 14px;
  color: #333;
}
.prop-desc-text {
  font-size: 13px;
  color: #666;
  margin-bottom: 4px;
  line-height: 1.4;
}
.type-tag {
  display: inline-block;
  background: #f0f0f0;
  padding: 2px 6px;
  border-radius: 3px;
  font-size: 12px;
  color: #777;
}
.default-val {
  font-family: monospace;
  background: #f5f5f5;
  color: #e83e8c;
  padding: 2px 4px;
  border-radius: 3px;
  font-size: 12px;
}

.sb-input,
.sb-textarea {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 13px;
  box-sizing: border-box;
  background-color: #fff;
}

select.sb-input {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  padding-right: 30px;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 24 24' fill='none' stroke='%23999' stroke-width='3' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M6 9l6 6 6-6'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: calc(100% - 10px) center;
  background-size: 12px;
  cursor: pointer;

  &:focus {
    outline: none;
    border-color: #000;
  }
}

.sb-textarea {
  font-family: monospace;
  resize: vertical;
  min-height: 80px;
}

.switch-container {
  display: flex;
  align-items: center;
  gap: 8px;
}
.switch {
  position: relative;
  display: inline-block;
  width: 34px;
  height: 18px;
  input {
    opacity: 0;
    width: 0;
    height: 0;
  }
}
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.2s;
  border-radius: 18px;
  &:before {
    position: absolute;
    content: '';
    height: 12px;
    width: 12px;
    left: 3px;
    bottom: 3px;
    background-color: white;
    transition: 0.2s;
    border-radius: 50%;
  }
}
input:checked + .slider {
  background-color: #000;
}
input:checked + .slider:before {
  transform: translateX(16px);
}

.sb-cases-section,
.sb-stories-section {
  margin-top: 40px;
  border-top: 1px solid #eee;
  padding-top: 32px;
  .cases-header {
    margin-bottom: 16px;
    .cases-desc {
      margin-top: 4px;
    }
  }
}
.stories-container {
  display: flex;
  flex-direction: column;
  gap: 40px;
}
.story-info {
  padding: 0 4px 16px 4px;
}
.story-name {
  font-size: 18px;
  font-weight: 700;
  color: #111;
  margin-bottom: 4px;
}
.story-desc {
  font-size: 14px;
  color: #666;
}
</style>
