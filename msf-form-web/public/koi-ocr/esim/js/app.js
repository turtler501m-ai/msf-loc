class EsimScanner {
  constructor(options = {}) {
    this.options = {
      totalEsimCount: options.totalEsimCount || 3,
      autoStart: options.autoStart !== false,
      debugMode: options.debugMode || false,
      ...options,
    }

    this.stream = null
    this.isScanning = false
    this.detectedEsimCount = 0
    this.totalEsimCount = this.options.totalEsimCount
    this.esimStates = []
    this.isProcessing = false
    this.timeoutDuration = 60000 // 1분 (밀리초)
    this.timeoutTimer = null
    this.startTime = null
    this.processor = esimProcessorInstance
    this.bindElements()
  }

  async init() {
    this.showLoading(true)
    this.initializeEsimStates()
    this.updateUI()

    try {
      await this.initializeCamera()
      this.showLoading(false)
      this.startScanning()
    } catch (error) {
      this.showLoading(false)
      console.error('초기화 실패:', error)
      alert('인식 모듈 또는 카메라 초기화에 실패했습니다.')
      //location.reload();
    }
  }

  bindElements() {
    this.elements = {
      video: document.getElementById('camera-feed'),
      progressCount: document.getElementById('progressCount'),
      progressFill: document.getElementById('progressFill'),
      esimStatusSimple: document.getElementById('esimStatusSimple'),

      // 기존 statusMessage 대신 새로운 ID들로 연결
      statusText: document.getElementById('statusText'),
      timerSeconds: document.getElementById('timerSeconds'),

      loadingIndicator: document.getElementById('loadingIndicator'),
      resultOverlay: document.getElementById('resultOverlay'),
      touchFeedback: document.getElementById('touchFeedback'),
    }
  }

  setupEventListeners() {
    // 터치 피드백
    document.addEventListener('touchstart', (e) => this.handleTouch(e))
    document.addEventListener('click', (e) => this.handleTouch(e))

    // 키보드 이벤트 (테스트용)
    document.addEventListener('keydown', (e) => {
      if (e.code === 'Space') {
        e.preventDefault()
        this.simulateDetection()
      } else if (e.code === 'KeyD' && this.options.debugMode) {
        console.log('Debug Info:', this.getDebugInfo())
      }
    })
  }

  handleTouch(event) {
    const touch = event.touches ? event.touches[0] : event
    const feedback = this.elements.touchFeedback

    feedback.style.left = touch.clientX - 20 + 'px'
    feedback.style.top = touch.clientY - 20 + 'px'
    feedback.classList.add('show')

    setTimeout(() => {
      feedback.classList.remove('show')
    }, 300)
  }

  async initializeCamera() {
    // 1. 브라우저 지원 확인
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
      throw new Error('브라우저가 카메라를 지원하지 않거나 보안 연결(HTTPS)이 아닙니다.')
    }

    const constraints = {
      audio: false,
      video: {
        facingMode: { ideal: 'environment' },
        zoom: true,
        focusMode: 'continuous',
        width: { ideal: 1920 },
        height: { ideal: 1080 },
        resizeMode: 'none',
      },
    }

    try {
      // 2. 카메라 스트림 먼저 가져오기
      this.stream = await navigator.mediaDevices.getUserMedia(constraints)
      this.elements.video.srcObject = this.stream

      // 3. 비디오가 실제로 재생될 때까지 대기
      await new Promise((resolve, reject) => {
        this.elements.video.onloadedmetadata = () => {
          this.elements.video.play().then(resolve).catch(reject)
        }
        this.elements.video.onerror = reject
      })

      console.log('카메라 활성화 완료')

      // 4. Wasm 프로세서 초기화 (카메라가 뜬 후 로드)
      // 경로가 실제 서버의 경로와 맞는지 확인 필수 (예: /wasm/ 또는 ./wasm/)
      const initRes = await this.processor.initModule('./wasm/', this.totalEsimCount)
      if (!initRes.success) {
        console.error('Wasm 세부 에러:', initRes.error)
        throw new Error('인식 모듈을 불러오지 못했습니다.')
      }

      console.log('Wasm 모듈 초기화 완료')
    } catch (error) {
      console.error('초기화 프로세스 실패:', error)
      throw error // 상위 init()의 catch로 전달됨
    }
  }

  initializeEsimStates() {
    this.esimStates = Array(this.totalEsimCount)
      .fill(null)
      .map((_, i) => ({
        id: i + 1,
        status: 'pending',
      }))
  }

  startScanning() {
    this.isScanning = true
    this.startTime = Date.now()

    // 1. 타임아웃 실행 타이머
    this.timeoutTimer = setTimeout(() => {
      if (this.isScanning && !this.isComplete()) {
        this.handleTimeout()
      }
    }, this.timeoutDuration)

    // 2. 실시간 남은 시간 표시 UI 업데이트 (1초 간격)
    this.startCountdown()

    this.beginAutoDetection()
    this.setupEventListeners()
  }

  startCountdown() {
    const update = () => {
      if (!this.isScanning) return

      const elapsed = Date.now() - this.startTime
      const remainingMs = Math.max(0, this.timeoutDuration - elapsed)
      const seconds = Math.ceil(remainingMs / 1000)

      // 1. 타이머 숫자만 업데이트
      if (this.elements.timerSeconds) {
        this.elements.timerSeconds.textContent = seconds
      }

      // 2. 남은 시간에 따라 메시지 색상 변경 (긴급 상황 피드백)
      if (seconds <= 10) {
        document.getElementById('timerBadge').style.color = '#ff4d4d'
        document.getElementById('timerBadge').style.borderColor = 'rgba(255, 77, 77, 0.5)'
      }

      if (remainingMs > 0) {
        this.countdownTimer = setTimeout(update, 1000)
      }
    }
    update()
  }

  updateStatusText(msg) {
    if (this.elements.statusText) {
      this.elements.statusText.textContent = msg
    }
  }

  handleTimeout() {
    this.isScanning = false
    this.stopTimers()
    this.updateStatusText('시간이 초과되었습니다.')
    this.showResultOverlay(false)
  }

  stopTimers() {
    if (this.timeoutTimer) clearTimeout(this.timeoutTimer)
    if (this.countdownTimer) clearTimeout(this.countdownTimer)
    if (this.scanTimer) clearTimeout(this.scanTimer)
  }

  beginAutoDetection() {
    const run = async () => {
      if (!this.isScanning || this.detectedEsimCount >= this.totalEsimCount) return

      if (this.shouldAttemptDetection()) {
        await this.attemptDetection()
      }

      // 분석이 끝난 후 1초 뒤에 다시 실행 (Interval보다 안전함)
      this.scanTimer = setTimeout(run, 1000)
    }

    run()
  }

  shouldAttemptDetection() {
    return this.stream && !this.isProcessing && this.detectedEsimCount < this.totalEsimCount
  }

  async attemptDetection() {
    if (this.isProcessing) return
    this.isProcessing = true
    this.showLoading(true)

    try {
      const canvas = document.createElement('canvas')
      canvas.width = this.elements.video.videoWidth
      canvas.height = this.elements.video.videoHeight
      const ctx = canvas.getContext('2d')
      ctx.drawImage(this.elements.video, 0, 0)

      const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height)
      const scanResult = this.processor.scanFrame(imageData, canvas.width, canvas.height)

      // 1. 현재까지 찾은 개수 파악
      let currentFound = 0
      if (scanResult.resultCode === '0000') {
        const fields = ['EID', 'IMEI', 'IMEI2', 'MEID', 'S/N']
        fields.forEach((f) => {
          if (scanResult[f]) currentFound++
        })
      } else {
        currentFound = scanResult.findBarcode || 0
      }

      // 2. 실시간 아이콘 동기화
      if (currentFound > this.detectedEsimCount) {
        const diff = currentFound - this.detectedEsimCount
        for (let i = 0; i < diff; i++) {
          if (this.detectedEsimCount < this.totalEsimCount) {
            this.onEsimDetected()
          }
        }
      }

      // 3. 상태별 처리
      if (scanResult.resultCode === '0000') {
        console.log('scanResult Success: ', scanResult)
        this.lastScanResult = scanResult // 결과 보관
        this.updateStatusText('모든 정보를 인식했습니다!')
        this.onAllEsimDetected()
      } else if (scanResult.resultCode === '1001') {
        const cCount = scanResult.findBarcode || 0
        this.updateStatusText(`인식 중... (${cCount}/${this.totalEsimCount} 완료)`)
      } else {
        this.updateStatusText('eSIM을 스캔하고 있습니다')
      }
    } catch (error) {
      console.error('감지 실패:', error)
    } finally {
      this.isProcessing = false
      this.showLoading(false)
    }
  }

  async simulateAIModel() {
    const processingTime = Math.random() * 1500 + 800
    await new Promise((resolve) => setTimeout(resolve, processingTime))

    const successRate = 0.85
    if (Math.random() > successRate) {
      throw new Error('인식 실패')
    }

    return Math.random() > 0.6 ? 1 : Math.random() > 0.8 ? 2 : 1
  }

  simulateDetection() {
    if (this.detectedEsimCount < this.totalEsimCount && !this.isProcessing) {
      this.onEsimDetected()
    }
  }

  onEsimDetected() {
    const nextIndex = this.detectedEsimCount
    if (nextIndex >= this.totalEsimCount) return

    this.esimStates[nextIndex].status = 'detected'
    this.detectedEsimCount++

    this.updateUI()
  }

  updateUI() {
    const progress = (this.detectedEsimCount / this.totalEsimCount) * 100
    if (this.elements.progressFill) this.elements.progressFill.style.width = `${progress}%`
    this.updateEsimIcons()
  }

  updateProgressBar() {
    const progress = (this.detectedEsimCount / this.totalEsimCount) * 100
    this.elements.progressCount.textContent = `${this.detectedEsimCount}/${this.totalEsimCount}`
    this.elements.progressFill.style.width = `${progress}%`
  }

  updateEsimIcons() {
    const container = this.elements.esimStatusSimple
    container.innerHTML = ''

    this.esimStates.forEach((esim, index) => {
      const icon = document.createElement('div')
      icon.className = 'esim-icon-simple'

      if (esim.status === 'detected') {
        icon.classList.add('detected')
        icon.innerHTML = '<i class="fas fa-check"></i>'
      } else if (index === this.detectedEsimCount) {
        icon.classList.add('current')
        icon.innerHTML = '<i class="fas fa-circle"></i>'
      } else {
        icon.innerHTML = '<i class="fas fa-circle"></i>'
      }

      container.appendChild(icon)
    })
  }

  onAllEsimDetected() {
    this.isScanning = false
    this.stopTimers()

    console.log('scanResult 원본:', this.lastScanResult)

    window.parent.postMessage(
      {
        type: 'ESIM_SCAN_RESULT',
        payload: {
          eid: this.lastScanResult?.EID || '',
          imei1: this.lastScanResult?.IMEI || '',
          imei2: this.lastScanResult?.IMEI2 || '',
        },
      },
      '*',
    )

    setTimeout(() => {
      this.showResultOverlay(true, this.lastScanResult)
    }, 800)
  }

  showResultOverlay(isSuccess, data = null) {
    const overlay = this.elements.resultOverlay
    const icon = document.getElementById('resultIcon')
    const title = document.getElementById('resultTitle')
    const message = document.getElementById('resultMessage')
    const dataContainer = document.getElementById('resultDataContainer')

    // 이전 데이터 초기화
    dataContainer.innerHTML = ''
    overlay.classList.add('show')

    if (isSuccess && data) {
      icon.className = 'result-icon success'
      icon.innerHTML = '<i class="fas fa-check-circle"></i>'
      title.textContent = '인식 완료'
      message.textContent = `모든 eSIM이 인식되었습니다. (총 ${this.detectedEsimCount}개)`

      // 결과 데이터 생성
      const fields = [
        { key: 'EID', label: 'EID' },
        { key: 'IMEI', label: 'IMEI' },
        { key: 'IMEI2', label: 'IMEI 2' },
        { key: 'MEID', label: 'MEID' },
        { key: 'S/N', label: 'Serial Number' },
      ]

      fields.forEach((field) => {
        if (data[field.key] && data[field.key] !== 'null') {
          const row = document.createElement('div')
          row.className = 'data-row'
          row.innerHTML = `
          <span class="data-label">${field.label}</span>
          <span class="data-value">${data[field.key]}</span>
        `
          dataContainer.appendChild(row)
        }
      })

      dataContainer.style.display = 'block'
    } else {
      // 실패 시 로직
      icon.className = 'result-icon failure'
      icon.style.color = '#ff4d4d'
      icon.innerHTML = '<i class="fas fa-times-circle"></i>'
      title.textContent = '인식 실패'
      const timeoutSec = Math.floor(this.timeoutDuration / 1000)
      message.textContent = `${timeoutSec}초 동안 정보를 찾지 못했습니다.`

      dataContainer.style.display = 'none'
    }
  }

  showLoading(show) {
    if (this.elements.loadingIndicator) {
      this.elements.loadingIndicator.classList.toggle('show', show)
    }
  }

  showError(message) {
    alert(message)
  }

  // 외부 인터페이스
  setTotalEsimCount(count) {
    this.totalEsimCount = Math.max(1, Math.min(10, count)) // 1-10개 제한
    this.detectedEsimCount = 0
    this.initializeEsimStates()
    this.updateUI()
  }

  getTotalEsimCount() {
    return this.totalEsimCount
  }

  getDetectedCount() {
    return this.detectedEsimCount
  }

  isComplete() {
    return this.detectedEsimCount >= this.totalEsimCount
  }

  // 테스트용 메서드
  simulateDetection() {
    if (this.detectedEsimCount < this.totalEsimCount && !this.isProcessing) {
      this.onEsimDetected()
    }
  }

  getDebugInfo() {
    return {
      totalEsimCount: this.totalEsimCount,
      detectedEsimCount: this.detectedEsimCount,
      isScanning: this.isScanning,
      isProcessing: this.isProcessing,
      isComplete: this.isComplete(),
      esimStates: this.esimStates,
      cameraActive: !!this.stream,
    }
  }

  // 외부 인터페이스 메서드들
  toggleCapture() {
    if (this.isComplete()) {
      this.showResultOverlay(true)
    } else {
      this.attemptDetection()
    }
  }

  completeProcess() {
    console.log('Esim 인식 프로세스 완료')
    console.log('결과:', this.esimStates)
    this.closeApp()
  }

  // === app.js 전역 함수 수정 ===
  async retryCapture() {
    if (!scanner) return

    // 타이머 정리
    this.stopTimers()

    // 카메라 종료
    if (this.stream) {
      this.stream.getTracks().forEach((track) => track.stop())
    }

    // 결과창 닫기
    document.getElementById('resultOverlay').classList.remove('show')

    // 상태 초기화
    this.detectedEsimCount = 0
    this.isScanning = false
    this.isProcessing = false
    this.lastScanResult = null

    // UI 초기화
    this.initializeEsimStates()
    this.updateUI()

    try {
      // 카메라 다시 시작
      await this.initializeCamera()

      // 다시 스캔 시작
      this.startScanning()

      console.log('재시도 완료')
    } catch (error) {
      console.error('재시도 실패:', error)
      alert('카메라 재초기화에 실패했습니다.')
    }
  }
  resetScanning() {
    if (this.timeoutTimer) clearTimeout(this.timeoutTimer)
    this.detectedEsimCount = 0
    this.isScanning = true
    this.isProcessing = false
    this.initializeEsimStates()
    this.updateUI()
  }

  closeApp() {
    if (this.stream) {
      this.stream.getTracks().forEach((track) => track.stop())
    }

    console.log('앱 종료')
    try {
      window.close()
    } catch (error) {
      console.log('창을 닫을 수 없습니다.')
    }
  }
}

// 전역 함수들
let scanner

function toggleCapture() {
  if (scanner) {
    scanner.toggleCapture()
  }
}

function completeProcess() {
  if (scanner) {
    scanner.completeProcess()
  }
}

function retryCapture() {
  if (scanner) {
    scanner.retryCapture()
  }
}

function closeApp() {
  if (scanner) {
    scanner.closeApp()
  }
}

// 동적으로 Esim 개수 설정하는 함수
function setEsimCount(count) {
  if (scanner) {
    scanner.setTotalEsimCount(count)
  }
}

// 현재 상태 확인하는 함수
function getScannerStatus() {
  if (scanner) {
    return {
      total: scanner.getTotalEsimCount(),
      detected: scanner.getDetectedCount(),
      complete: scanner.isComplete(),
    }
  }
  return null
}

// 앱 초기화
document.addEventListener('DOMContentLoaded', async () => {
  const mainScreen = document.getElementById('mainScreen')
  const scanScreen = document.getElementById('scanScreen')

  const selectedCount = 3

  // 메인 화면 숨김
  mainScreen.style.display = 'none'

  // 스캔 화면 표시
  scanScreen.style.display = 'flex'

  // 스캐너 생성 및 자동 시작
  scanner = new EsimScanner({
    totalEsimCount: selectedCount,
    autoStart: true,
  })

  await scanner.init()
})

// 페이지 벗어날 때 카메라 정리
window.addEventListener('beforeunload', () => {
  if (scanner && scanner.stream) {
    scanner.stream.getTracks().forEach((track) => track.stop())
  }
  // if (this.processor) {
  //   this.processor.unload();
  //   this.processor = null;
  // }
})
