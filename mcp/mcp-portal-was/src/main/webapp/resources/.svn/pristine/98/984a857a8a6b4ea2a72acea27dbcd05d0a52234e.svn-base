var ns = (window.miniGame = {});
ns.vars = {
  el: null,
  resetBtn: null,
  gameOverEl: null,
  currentScoreEl: null,
  highScoreEl: null,

  isPlaying: false,
  cloudX: 0,
  roadX: 0,
  player: null,
  resource: {
    cloud: null,
    road: null,
    playerIdle: null,
    playerJump: null,
    obstacle1: null,
    obstacle2: null,
    obstacle3: null,
  },
  info: {
    lv: 0,
    scrore: 0,
    highScore: 0,
    gameSpeed: 1.3,
    respawnTime: 240,
  },
  LV_DESIGN: [
    {
      gameSpeed: 4,
      respawnTime: 120,
      playerGravity: 0.5,
      playerLift: -11,
      playerVelocity: 1,
      obstacleSize: [20, 39],
    },
    {
      gameSpeed: 6,
      respawnTime: 60,
      playerGravity: 0.7,
      playerLift: -11,
      playerVelocity: 1,
      obstacleSize: [20, 39],
    },
    {
      gameSpeed: 8,
      respawnTime: 50,
      playerGravity: 0.7,
      playerLift: -11,
      playerVelocity: 1,
      obstacleSize: [20, 39],
    },
    {
      gameSpeed: 10,
      respawnTime: 50,
      playerGravity: 0.7,
      playerLift: -11,
      playerVelocity: 1,
      obstacleSize: [33, 49],
    },
    {
      gameSpeed: 10,
      respawnTime: 40,
      playerGravity: 0.7,
      playerLift: -11,
      playerVelocity: 1,
      obstacleSize: [39, 59],
    },
  ],
  frameCount: 0,
  obstacles: [],
  CURRENT_COUNT: 0,
  LV_UP_COUNT: 10,
  CLOUD_SPEED: 0.35,
  RESPAWN_TIME: 240, // frame
};

function setup() {
  frameRate(60);
  var gameCanvas = createCanvas(1100, 320);
  gameCanvas.parent('game_canvas');
  noLoop();
}

function preload() {
  ns.vars.resource.cloud = loadImage('../../resources/images/portal/game/bg.jpg');
  ns.vars.resource.road = loadImage('../../resources/images/portal/game/road.jpg');
  ns.vars.resource.playerIdle = loadImage('../../resources/images/portal/game/player.png');
  ns.vars.resource.playerJump = loadImage('../../resources/images/portal/game/jump.png');

  ns.vars.resource.obstacle1 = loadImage('../../resources/images/portal/game/obstacle.jpg');
  ns.vars.resource.obstacle2 = loadImage('../../resources/images/portal/game/obstacle2.jpg');
  ns.vars.resource.obstacle3 = loadImage('../../resources/images/portal/game/obstacle3.jpg');
}

function draw() {
  background(255);
  if (ns.vars.isPlaying) {
    var obstacles = ns.vars.obstacles;
    ns.renderCloud();
    ns.renderRoad();
    ns.respawnObstacle();
    for (var i = obstacles.length - 1; i > -1; i--) {
      obstacles[i].draw();
      obstacles[i].update();
      if (obstacles[i].isHit()) {
        ns.endGame();
        break;
      }
      if (obstacles[i].isOut()) {
        ns.updateScore();
        obstacles.splice(i, 1);
      }
    }
    ns.vars.player.draw();
    ns.vars.player.update();
  }
}

ns.init = function (el) {
  ns.vars.obstacles = [];
  ns.vars.el = el;
  ns.vars.gameOverEl = ns.vars.el.querySelector('.game-over-cover');
  ns.vars.resetBtn = document.querySelector('#game_reset_btn');
  ns.vars.currentScoreEl = ns.vars.el.querySelector('#game_score_current');
  ns.vars.highScoreEl = ns.vars.el.querySelector('#game_score_high');

  ns.vars.resetBtn.addEventListener('click', ns.start);
  ns.vars.el.addEventListener('mousedown', ns.startJump);
  window.addEventListener('keydown', ns.downSpace);

  ns.vars.player = new ns.Player();
  ns.start(0);
};

ns.start = function (event, delay) {
  // 게임정보 리셋
  ns.vars.info.lv = 0; //3;
  var lv = ns.vars.info.lv;
  ns.vars.info.gameSpeed = ns.vars.LV_DESIGN[lv].gameSpeed;
  ns.vars.info.respawnTime = ns.vars.LV_DESIGN[lv].respawnTime;
  ns.vars.frameCount = 0;
  ns.vars.info.scrore = 0;
  ns.vars.CURRENT_COUNT = 0;
  ns.vars.gameOverEl.classList.remove('show');
  ns.vars.obstacles = [];
  var playerGravity = ns.vars.LV_DESIGN[lv].playerGravity;
  var playerLift = ns.vars.LV_DESIGN[lv].playerLift;
  var playerVelocity = ns.vars.LV_DESIGN[lv].playerVelocity;
  ns.vars.player.lvDesign(playerGravity, playerLift, playerVelocity);
  ns.vars.player.reset();
  if (event) {
    setTimeout(function () {
      ns.vars.isPlaying = true;
      ns.printScore();
      loop();
    }, 500);
  } else {
    ns.vars.isPlaying = true;
    ns.printScore();
    loop();
  }
};

ns.stop = function () {
  ns.vars.isPlaying = false;
  noLoop();
};

ns.startJump = function () {
  if (ns.vars.player && ns.vars.isPlaying === true) {
    ns.vars.player.jump();
  }
};
ns.downSpace = function (e) {
  e.preventDefault();
  if (e.keyCode === 32) {
    ns.startJump();
  }
};

ns.updateScore = function () {
  ns.vars.info.scrore += 100;
  ns.vars.CURRENT_COUNT++;
  if (ns.vars.CURRENT_COUNT === ns.vars.LV_UP_COUNT) {
    ns.vars.frameCount = 0;
    ns.vars.CURRENT_COUNT = 0;
    ns.vars.info.lv = Math.min(ns.vars.info.lv + 1, ns.vars.LV_DESIGN.length - 1);
    ns.vars.info.gameSpeed = ns.vars.LV_DESIGN[ns.vars.info.lv].gameSpeed;
    ns.vars.info.respawnTime = ns.vars.LV_DESIGN[ns.vars.info.lv].respawnTime;
    var playerGravity = ns.vars.LV_DESIGN[ns.vars.info.lv].playerGravity;
    var playerLift = ns.vars.LV_DESIGN[ns.vars.info.lv].playerLift;
    var playerVelocity = ns.vars.LV_DESIGN[ns.vars.info.lv].playerVelocity;
    ns.vars.player.lvDesign(playerGravity, playerLift, playerVelocity);
  }
  ns.printScore();
};

ns.printScore = function () {
  ns.vars.currentScoreEl.textContent = ns.vars.info.scrore + '점';
  ns.vars.info.highScore = Math.max(ns.vars.info.scrore, ns.vars.info.highScore);
  ns.vars.highScoreEl.textContent = '최고기록: ' + ns.vars.info.highScore + '점';
};

ns.endGame = function () {
  noLoop();
  ns.vars.isPlaying = false;
  var modalContent = ns.vars.el.closest('.c-modal__content');
  var gameOverPanel = ns.vars.el.querySelector('.game-over-cover');
  modalContent.classList.add('shake');

  setTimeout(function () {
    modalContent.classList.remove('shake');
    gameOverPanel.classList.add('show');
  }, 300);
};

// 구름 스크롤링
ns.renderCloud = function () {
  image(ns.vars.resource.cloud, ns.vars.cloudX, 80, width, 80);
  image(ns.vars.resource.cloud, ns.vars.cloudX + width, 80, width, 80);

  ns.vars.cloudX -= ns.vars.CLOUD_SPEED;
  if (ns.vars.cloudX < -width) {
    ns.vars.cloudX = 0;
  }
};

// 바닥 길 스크롤링
ns.renderRoad = function () {
  image(ns.vars.resource.road, ns.vars.roadX, 299, width, 21);
  image(ns.vars.resource.road, ns.vars.roadX + width, 299, width, 21);

  ns.vars.roadX -= ns.vars.info.gameSpeed;
  if (ns.vars.roadX <= -width) {
    ns.vars.roadX = 0;
  }
};

ns.respawnObstacle = function () {
  ns.vars.frameCount = ns.vars.frameCount + 1;
  if (ns.vars.frameCount % ns.vars.info.respawnTime === 0) {
    setTimeout(function () {
      ns.vars.obstacles.push(new ns.Obstacle());
    }, floor(random(0, 30)));
  }
};

ns.pause = function () {
  noLoop();
};
ns.resume = function () {
  loop();
};

ns.destroy = function () {
  if (ns.vars.resetBtn) ns.vars.resetBtn.removeEventListener('click', ns.start);
  if (ns.vars.el) {
    ns.vars.el.removeEventListener('mousedown', ns.startJump);
    window.removeEventListener('keydown', ns.downSpace);
  }

  noLoop();
  ns.vars.el = null;
  ns.vars.resetBtn = null;
  ns.vars.gameOverEl = null;
  ns.vars.currentScoreEl = null;
  ns.vars.highScoreEl = null;
  ns.vars.isPlaying = false;
  ns.vars.player = null;
  // ns.vars.resource.cloud = null;
  // ns.vars.resource.road = null;
  // ns.vars.resource.playerIdle = null;
  // ns.vars.resource.playerJump = null;
  // ns.vars.resource.obstacle1 = null;
  // ns.vars.resource.obstacle2 = null;
  // ns.vars.resource.obstacle3 = null;
  ns.vars.isPlaying = false;
  ns.vars.obstacles = [];
};

// 플레이어 설정 및 렌더링
ns.Player = function () {
  this.size = 54;
  this.diffSize = this.size + 21; // 캐릭터 사이즈 + 길 높이 값
  this.y = height - this.diffSize;
  this.x = 50;
  this.frameStep = 0;
  this.frameTotal = 2 - 1;
  this.gravity = 0.2;
  this.lift = -8;
  this.velocity = 1;
  this.state = '';
  this.img = null;

  this.lvDesign = function (g, l, v) {
    this.gravity = g;
    this.lift = l;
    this.velocity = v;
  };

  this.reset = function () {
    this.y = 0;
    this.x = 50;
    this.frameStep = 0;
  };

  this.draw = function () {
    // 시퀀스 별로 분리하여 draw
    var img = this.state === 'jump' ? ns.vars.resource.playerJump : ns.vars.resource.playerIdle;

    if (this.img !== img) {
      this.frameStep = 0;
    }

    // 시퀀스 이미지 프레임별로 짤라서 플레이( 컷 애니메이션 )
    var fC = ns.vars.info.lv === 0 ? 20 : 10;
    if (this.state === 'run') {
      if (frameCount % fC === 0) {
        this.frameStep++;
        if (this.frameStep > this.frameTotal) {
          this.frameStep = 0;
        }
      }
    }

    this.img = img;

    image(this.img, this.x, this.y - this.diffSize, this.size, this.size, this.size * this.frameStep, 0, this.size, this.size);
  };

  this.update = function () {
    this.velocity += this.gravity;
    this.y += this.velocity;

    if (this.y > height) {
      this.state = 'run';
      this.y = height;
      this.velocity = 0;
    } else {
      this.state = 'jump';
    }
  };

  this.jump = function () {
    if (this.y == height) this.velocity += this.lift;
  };
};

ns.Obstacle = function () {
  var s = ns.vars.LV_DESIGN[ns.vars.info.lv].obstacleSize;
  this.x = width;
  this.width = floor(random(s[0], s[1]));
  this.height = this.width;
  this.random = floor(random(0, 2));
  this.img = null;
  this.diffSize = this.width + 21; // 캐릭터 사이즈 + 길 높이 값
  this.y = height - this.diffSize;
  if (this.random === 0) {
    this.img = ns.vars.resource.obstacle1;
  } else if (this.random === 1) {
    this.img = ns.vars.resource.obstacle2;
  } else {
    this.img = ns.vars.resource.obstacle3;
  }

  this.draw = function () {
    image(this.img, this.x, this.y, this.width, this.height);
  };

  this.update = function () {
    this.x -= ns.vars.info.gameSpeed;
  };

  this.isHit = function () {
    return ns.vars.player.x + 15 <= this.x + this.width && ns.vars.player.x + 15 + 24 >= this.x && ns.vars.player.y - (54 + 21) < this.y + this.height && ns.vars.player.y - (54 + 21) + 54 >= this.y;
  };

  this.isOut = function () {
    return this.x < -this.width;
  };
};
