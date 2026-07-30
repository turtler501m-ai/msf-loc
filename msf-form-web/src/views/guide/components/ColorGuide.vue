<template>
  <div class="guide-page">
    <GuideUnit
      name="ColorGuide"
      title="Color"
      description="컬러 디자인 가이드"
      caseTitle="Color Token List"
      caseDescription="CSS 속성값에 var(--color-primary-base) 형태로 사용합니다."
    >
      <template #cases>
        <div class="color-guide">
          <section v-for="group in colorGroups" :key="group.title" class="token-section">
            <div class="token-section-header">
              <h3 class="token-section-title">{{ group.title }}</h3>
              <span class="token-section-count">{{ group.tokens.length }}</span>
            </div>

            <div class="token-set-list">
              <article v-for="set in group.sets" :key="set.name" class="token-set">
                <h4 class="token-set-name">{{ set.name }}</h4>
                <div
                  class="token-set-grid"
                  :class="{
                    'is-single': set.tokens.length === 1,
                    'is-double': set.tokens.length === 2,
                  }"
                >
                  <div v-for="(token, tokenIndex) in set.tokens" :key="token" class="token-item">
                    <div class="token-swatch">
                      <span class="token-fill" :style="{ backgroundColor: `var(${token})` }"></span>
                    </div>
                    <div class="token-info">
                      <span v-if="set.showRoles" class="token-role">
                        {{ tokenRoles[tokenIndex] }}
                      </span>
                      <code class="token-code">var({{ token }})</code>
                      <code class="token-value">{{ tokenValues[token] }}</code>
                    </div>
                  </div>
                </div>
              </article>
            </div>
          </section>

          <section class="token-section">
            <div class="token-section-header">
              <h3 class="token-section-title">Grayscale</h3>
              <span class="token-section-count">{{ grayTokens.length }}</span>
            </div>

            <div class="gray-scale-strip">
              <div
                v-for="token in grayTokens"
                :key="token"
                class="gray-scale-chip"
                :title="`var(${token})`"
                :style="{ backgroundColor: `var(${token})` }"
              ></div>
            </div>

            <div class="token-grid is-compact">
              <article v-for="token in grayTokens" :key="token" class="token-item">
                <div class="token-swatch is-small">
                  <span class="token-fill" :style="{ backgroundColor: `var(${token})` }"></span>
                </div>
                <div class="token-info">
                  <code class="token-code">var({{ token }})</code>
                  <code class="token-value">{{ tokenValues[token] }}</code>
                </div>
              </article>
            </div>
          </section>

          <section class="token-section">
            <div class="token-section-header">
              <h3 class="token-section-title">Alpha</h3>
              <span class="token-section-count">{{ alphaTokens.length }}</span>
            </div>

            <div class="token-grid">
              <article v-for="token in alphaTokens" :key="token" class="token-item">
                <div class="token-swatch is-checker">
                  <span class="token-fill" :style="{ backgroundColor: `var(${token})` }"></span>
                </div>
                <div class="token-info">
                  <code class="token-code">var({{ token }})</code>
                  <code class="token-value">{{ tokenValues[token] }}</code>
                </div>
              </article>
            </div>
          </section>
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const tokenRoles = ['base', 'hover', 'press']

const colorGroups = [
  {
    title: 'Base',
    sets: [
      { name: 'Default', tokens: ['--color-black', '--color-white'] },
      { name: 'Surface', tokens: ['--color-foreground', '--color-background'] },
    ],
  },
  {
    title: 'Background',
    sets: [
      {
        name: 'BG',
        tokens: [
          '--color-bg-white',
          '--color-bg-gray',
          '--color-bg-disabled',
          '--color-bg-1',
          '--color-bg-2',
          '--color-bg-3',
        ],
      },
    ],
  },
  {
    title: 'Brand',
    sets: [
      {
        name: 'Primary',
        tokens: ['--color-primary-base', '--color-primary-hover', '--color-primary-press'],
        showRoles: true,
      },
      {
        name: 'Secondary',
        tokens: ['--color-secondary-base', '--color-secondary-hover', '--color-secondary-press'],
        showRoles: true,
      },
      {
        name: 'Tertiary',
        tokens: ['--color-tertiary-base', '--color-tertiary-hover', '--color-tertiary-press'],
        showRoles: true,
      },
    ],
  },
  {
    title: 'Accent',
    sets: [
      {
        name: 'Accent 1',
        tokens: ['--color-accent1-base', '--color-accent1-hover', '--color-accent1-press'],
        showRoles: true,
      },
      {
        name: 'Accent 2',
        tokens: ['--color-accent2-base', '--color-accent2-hover', '--color-accent2-press'],
        showRoles: true,
      },
      {
        name: 'Semantic',
        tokens: [
          '--color-accent-base',
          '--color-accent-alert',
          '--color-accent-caret',
          '--color-accent-success',
          '--color-accent-warning',
          '--color-accent-info',
          '--color-accent-error',
          '--color-accent-purple',
          '--color-accent-1',
        ],
      },
    ],
  },
  {
    title: 'Text / Line',
    sets: [
      {
        name: 'Text',
        tokens: ['--color-text-disabled', '--color-text-readonly', '--color-text-placeholder'],
      },
      {
        name: 'Line',
        tokens: ['--color-line-disabled'],
      },
    ],
  },
  {
    title: 'Subtle',
    sets: [
      {
        name: 'Subtle',
        tokens: ['--color-subtle-base', '--color-subtle-hover', '--color-subtle-press'],
        showRoles: true,
      },
    ],
  },
]

colorGroups.forEach((group) => {
  group.tokens = group.sets.flatMap((set) => set.tokens)
})

const grayTokens = [
  '--color-gray-900',
  '--color-gray-850',
  '--color-gray-800',
  '--color-gray-750',
  '--color-gray-700',
  '--color-gray-650',
  '--color-gray-600',
  '--color-gray-550',
  '--color-gray-500',
  '--color-gray-450',
  '--color-gray-400',
  '--color-gray-350',
  '--color-gray-300',
  '--color-gray-250',
  '--color-gray-200',
  '--color-gray-150',
  '--color-gray-100',
  '--color-gray-75',
  '--color-gray-50',
  '--color-gray-25',
]

const alphaTokens = [
  '--color-alpha-w30',
  '--color-alpha-w60',
  '--color-alpha-w80',
  '--color-alpha-b30',
  '--color-alpha-b50',
  '--color-alpha-b60',
  '--color-alpha-b70',
  '--color-alpha-dim',
]

const allColorTokens = [
  ...new Set([...colorGroups.flatMap((group) => group.tokens), ...grayTokens, ...alphaTokens]),
]

const tokenValues = ref({})

const syncTokenValues = () => {
  const rootStyle = getComputedStyle(document.documentElement)

  tokenValues.value = allColorTokens.reduce((values, token) => {
    values[token] = rootStyle.getPropertyValue(token).trim() || '-'
    return values
  }, {})
}

onMounted(async () => {
  await nextTick()
  syncTokenValues()
})
</script>

<style lang="scss" scoped>
.color-guide {
  display: flex;
  flex-direction: column;
  gap: 24px;
}
.token-section {
  overflow: hidden;
  border: 1px solid #f0f0f0;
  border-radius: 6px;
  background: #fff;
}
.token-section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 44px;
  padding: 0 16px;
  background: #f8f9fa;
  border-bottom: 1px solid #f0f0f0;
}
.token-section-title {
  margin: 0;
  color: #111;
  font-size: 15px;
  font-weight: 600;
}
.token-section-count {
  color: #7a7e86;
  font-size: 12px;
  font-weight: 600;
}
.token-grid,
.token-set-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1px;
  background: #f0f0f0;

  &.is-compact {
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  }
}
.token-set-list {
  display: flex;
  flex-direction: column;
}
.token-set {
  display: grid;
  grid-template-columns: 120px minmax(0, 1fr);
  background: #fff;
  & + & {
    border-top: 1px solid #f0f0f0;
  }
}
.token-set-name {
  margin: 0;
  padding: 14px 16px;
  background: #fcfcfc;
  color: #55585d;
  font-size: 13px;
  font-weight: 600;
}
.token-set-grid {
  grid-template-columns: repeat(3, minmax(0, 1fr));
  border-left: 1px solid #f0f0f0;
  &.is-single {
    grid-template-columns: minmax(0, 1fr);
  }
  &.is-double {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
.token-item {
  display: grid;
  grid-template-columns: 36px minmax(0, 1fr);
  align-items: start;
  min-width: 0;
  gap: 10px;
  padding: 12px;
  background: #fff;
}
.token-swatch {
  position: relative;
  width: 36px;
  height: 36px;
  overflow: hidden;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.08);
  &.is-small {
    height: 28px;
  }
  &.is-checker {
    background-image:
      linear-gradient(45deg, #e9eaee 25%, transparent 25%),
      linear-gradient(-45deg, #e9eaee 25%, transparent 25%),
      linear-gradient(45deg, transparent 75%, #e9eaee 75%),
      linear-gradient(-45deg, transparent 75%, #e9eaee 75%);
    background-position:
      0 0,
      0 5px,
      5px -5px,
      -5px 0;
    background-size: 10px 10px;
  }
}
.token-fill {
  position: absolute;
  inset: 0;
}
.token-info {
  display: flex;
  flex-direction: column;
  min-width: 0;
  gap: 3px;
}
.token-role {
  width: fit-content;
  padding: 1px 6px;
  border-radius: 999px;
  background: #f3f4f6;
  color: #55585d;
  font-size: 9px;
  font-weight: 600;
  line-height: 1.5;
  letter-spacing: 0;
  text-transform: uppercase;
}
.token-code,
.token-value {
  overflow: hidden;
  padding: 0;
  background: transparent;
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
  line-height: 1.45;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.token-code {
  color: #191a1b;
  font-size: 13px;
  font-weight: 500;
}
.token-value {
  color: #55585d;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0;
}
.gray-scale-strip {
  display: grid;
  grid-template-columns: repeat(20, minmax(0, 1fr));
  gap: 1px;
  height: 28px;
  background: #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
}
.gray-scale-chip {
  min-width: 0;
}
@media (max-width: 720px) {
  .token-set {
    grid-template-columns: 1fr;
  }
  .token-set-name {
    padding: 10px 12px;
    border-bottom: 1px solid #f0f0f0;
  }
  .token-set-grid {
    grid-template-columns: 1fr;
    border-left: 0;
  }
}
</style>
