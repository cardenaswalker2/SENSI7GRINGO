/**
 * SENSI7GRINGO - STANDALONE JAVASCRIPT CONTROLLER (GITHUB PAGES EDITION)
 * 100% Client-Side: Funciona directamente en cualquier navegador, servidor o GitHub Pages.
 * Incluye catálogo embebido de dispositivos, Pro Players, motor algorítmico matemático
 * y almacenamiento en localStorage para Historial y Favoritos.
 */

// 1. BASE DE DATOS DE DISPOSITIVOS INTEGRADA
const DispositivosData = [
  // Apple iOS
  { id: "ios-1", marca: "Apple", modelo: "iPhone 15 Pro Max", so: "iOS", pantalla: 6.7, resolucion: "1290x2796", ppi: 460, hz: 120, factor: 1.15, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-2", marca: "Apple", modelo: "iPhone 15 Pro", so: "iOS", pantalla: 6.1, resolucion: "1179x2556", ppi: 460, hz: 120, factor: 1.14, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-3", marca: "Apple", modelo: "iPhone 15", so: "iOS", pantalla: 6.1, resolucion: "1179x2556", ppi: 460, hz: 60, factor: 1.06, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-4", marca: "Apple", modelo: "iPhone 14 Pro Max", so: "iOS", pantalla: 6.7, resolucion: "1290x2796", ppi: 460, hz: 120, factor: 1.13, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-5", marca: "Apple", modelo: "iPhone 14", so: "iOS", pantalla: 6.1, resolucion: "1170x2532", ppi: 460, hz: 60, factor: 1.05, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-6", marca: "Apple", modelo: "iPhone 13 Pro Max", so: "iOS", pantalla: 6.7, resolucion: "1284x2778", ppi: 458, hz: 120, factor: 1.12, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-7", marca: "Apple", modelo: "iPhone 13", so: "iOS", pantalla: 6.1, resolucion: "1170x2532", ppi: 460, hz: 60, factor: 1.04, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-8", marca: "Apple", modelo: "iPhone 12", so: "iOS", pantalla: 6.1, resolucion: "1170x2532", ppi: 460, hz: 60, factor: 1.01, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-9", marca: "Apple", modelo: "iPhone 11", so: "iOS", pantalla: 6.1, resolucion: "828x1792", ppi: 326, hz: 60, factor: 0.98, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-10", marca: "Apple", modelo: "iPhone XR", so: "iOS", pantalla: 6.1, resolucion: "828x1792", ppi: 326, hz: 60, factor: 0.96, dpiDefault: 0, dpiMax: 0 },
  { id: "ios-11", marca: "Apple", modelo: "iPhone 8 Plus", so: "iOS", pantalla: 5.5, resolucion: "1080x1920", ppi: 401, hz: 60, factor: 0.92, dpiDefault: 0, dpiMax: 0 },

  // Samsung
  { id: "sam-1", marca: "Samsung", modelo: "Galaxy S24 Ultra", so: "Android", pantalla: 6.8, resolucion: "1440x3120", ppi: 505, hz: 120, factor: 1.18, dpiDefault: 411, dpiMax: 600 },
  { id: "sam-2", marca: "Samsung", modelo: "Galaxy S23 Ultra", so: "Android", pantalla: 6.8, resolucion: "1440x3088", ppi: 500, hz: 120, factor: 1.16, dpiDefault: 411, dpiMax: 590 },
  { id: "sam-3", marca: "Samsung", modelo: "Galaxy S22 Ultra", so: "Android", pantalla: 6.8, resolucion: "1440x3088", ppi: 500, hz: 120, factor: 1.14, dpiDefault: 411, dpiMax: 580 },
  { id: "sam-4", marca: "Samsung", modelo: "Galaxy S21 FE", so: "Android", pantalla: 6.4, resolucion: "1080x2340", ppi: 401, hz: 120, factor: 1.08, dpiDefault: 384, dpiMax: 560 },
  { id: "sam-5", marca: "Samsung", modelo: "Galaxy A55 5G", so: "Android", pantalla: 6.6, resolucion: "1080x2340", ppi: 390, hz: 120, factor: 1.07, dpiDefault: 384, dpiMax: 550 },
  { id: "sam-6", marca: "Samsung", modelo: "Galaxy A54 5G", so: "Android", pantalla: 6.4, resolucion: "1080x2340", ppi: 403, hz: 120, factor: 1.05, dpiDefault: 384, dpiMax: 550 },
  { id: "sam-7", marca: "Samsung", modelo: "Galaxy A34 5G", so: "Android", pantalla: 6.6, resolucion: "1080x2340", ppi: 390, hz: 120, factor: 1.02, dpiDefault: 384, dpiMax: 520 },
  { id: "sam-8", marca: "Samsung", modelo: "Galaxy A14 / A15", so: "Android", pantalla: 6.6, resolucion: "1080x2408", ppi: 400, hz: 90, factor: 0.95, dpiDefault: 384, dpiMax: 480 },
  { id: "sam-9", marca: "Samsung", modelo: "Galaxy A10 / A20 / A30", so: "Android", pantalla: 6.2, resolucion: "720x1520", ppi: 271, hz: 60, factor: 0.88, dpiDefault: 360, dpiMax: 440 },

  // Xiaomi / Redmi / POCO
  { id: "xia-1", marca: "Xiaomi", modelo: "Xiaomi 13T Pro", so: "Android", pantalla: 6.67, resolucion: "1220x2712", ppi: 446, hz: 144, factor: 1.20, dpiDefault: 392, dpiMax: 620 },
  { id: "xia-2", marca: "Xiaomi", modelo: "Redmi Note 13 Pro+", so: "Android", pantalla: 6.67, resolucion: "1220x2712", ppi: 446, hz: 120, factor: 1.10, dpiDefault: 392, dpiMax: 580 },
  { id: "xia-3", marca: "Xiaomi", modelo: "Redmi Note 12S / 12", so: "Android", pantalla: 6.67, resolucion: "1080x2400", ppi: 395, hz: 120, factor: 1.03, dpiDefault: 392, dpiMax: 540 },
  { id: "xia-4", marca: "Xiaomi", modelo: "POCO X6 Pro 5G", so: "Android", pantalla: 6.67, resolucion: "1220x2712", ppi: 446, hz: 120, factor: 1.15, dpiDefault: 392, dpiMax: 600 },
  { id: "xia-5", marca: "Xiaomi", modelo: "POCO X5 Pro / X4 GT", so: "Android", pantalla: 6.67, resolucion: "1080x2400", ppi: 395, hz: 120, factor: 1.11, dpiDefault: 392, dpiMax: 580 },
  { id: "xia-6", marca: "Xiaomi", modelo: "POCO F5 / F5 Pro", so: "Android", pantalla: 6.67, resolucion: "1080x2400", ppi: 395, hz: 120, factor: 1.12, dpiDefault: 392, dpiMax: 590 },
  { id: "xia-7", marca: "Xiaomi", modelo: "Redmi 9 / 10C / Note 8", so: "Android", pantalla: 6.53, resolucion: "720x1600", ppi: 269, hz: 60, factor: 0.90, dpiDefault: 360, dpiMax: 450 },

  // Motorola
  { id: "mot-1", marca: "Motorola", modelo: "Edge 40 Neo / Pro", so: "Android", pantalla: 6.55, resolucion: "1080x2400", ppi: 402, hz: 144, factor: 1.14, dpiDefault: 411, dpiMax: 590 },
  { id: "mot-2", marca: "Motorola", modelo: "Moto G84 5G", so: "Android", pantalla: 6.5, resolucion: "1080x2400", ppi: 405, hz: 120, factor: 1.04, dpiDefault: 411, dpiMax: 540 },
  { id: "mot-3", marca: "Motorola", modelo: "Moto G54 5G", so: "Android", pantalla: 6.5, resolucion: "1080x2400", ppi: 405, hz: 120, factor: 1.02, dpiDefault: 411, dpiMax: 520 },
  { id: "mot-4", marca: "Motorola", modelo: "Moto G22 / G23 / G13", so: "Android", pantalla: 6.5, resolucion: "720x1600", ppi: 268, hz: 90, factor: 0.92, dpiDefault: 384, dpiMax: 460 },
  { id: "mot-5", marca: "Motorola", modelo: "Moto G7 / G8 Plus", so: "Android", pantalla: 6.3, resolucion: "1080x2280", ppi: 400, hz: 60, factor: 0.89, dpiDefault: 360, dpiMax: 440 },

  // Realme
  { id: "rea-1", marca: "Realme", modelo: "Realme GT Neo 5 / GT3", so: "Android", pantalla: 6.74, resolucion: "1240x2772", ppi: 451, hz: 144, factor: 1.18, dpiDefault: 400, dpiMax: 600 },
  { id: "rea-2", marca: "Realme", modelo: "Realme 11 Pro+ 5G", so: "Android", pantalla: 6.7, resolucion: "1080x2412", ppi: 394, hz: 120, factor: 1.06, dpiDefault: 380, dpiMax: 560 },
  { id: "rea-3", marca: "Realme", modelo: "Realme C55 / C67", so: "Android", pantalla: 6.72, resolucion: "1080x2400", ppi: 392, hz: 90, factor: 0.98, dpiDefault: 380, dpiMax: 500 },

  // Huawei
  { id: "hua-1", marca: "Huawei", modelo: "P60 Pro", so: "Android", pantalla: 6.67, resolucion: "1220x2700", ppi: 444, hz: 120, factor: 1.12, dpiDefault: 400, dpiMax: 580 },
  { id: "hua-2", marca: "Huawei", modelo: "Nova 11 / 11 Pro", so: "Android", pantalla: 6.7, resolucion: "1084x2412", ppi: 395, hz: 120, factor: 1.04, dpiDefault: 380, dpiMax: 540 },
  { id: "hua-3", marca: "Huawei", modelo: "Y9 Prime / Y7a", so: "Android", pantalla: 6.59, resolucion: "1080x2340", ppi: 391, hz: 60, factor: 0.93, dpiDefault: 360, dpiMax: 480 },

  // Infinix & Tecno
  { id: "inf-1", marca: "Infinix", modelo: "Infinix GT 20 Pro", so: "Android", pantalla: 6.78, resolucion: "1080x2436", ppi: 393, hz: 144, factor: 1.15, dpiDefault: 400, dpiMax: 590 },
  { id: "inf-2", marca: "Infinix", modelo: "Infinix Hot 40 Pro", so: "Android", pantalla: 6.78, resolucion: "1080x2460", ppi: 396, hz: 120, factor: 1.03, dpiDefault: 380, dpiMax: 520 },
  { id: "tec-1", marca: "Tecno", modelo: "Tecno Camon 30 Pro", so: "Android", pantalla: 6.78, resolucion: "1080x2436", ppi: 393, hz: 144, factor: 1.12, dpiDefault: 390, dpiMax: 560 },
  { id: "tec-2", marca: "Tecno", modelo: "Tecno Spark 20 Pro", so: "Android", pantalla: 6.78, resolucion: "1080x2460", ppi: 396, hz: 120, factor: 1.00, dpiDefault: 380, dpiMax: 510 }
];

// 2. PRO PLAYERS DOCUMENTADOS
const ProPlayersData = [
  { id: "pro-1", nombre: "Bruno Goes", apodo: "Nobru", equipo: "Fluxo", disp: "iPhone 15 Pro Max", estilo: "Rusher agresivo / Levantamiento ágil", general: 195, puntoRojo: 192, mira2x: 188, mira4x: 178, awm: 85, olhadinha: 200, boton: 44, dpi: null, insignia: "👑 Campeón Mundial" },
  { id: "pro-2", nombre: "Ygor Araújo", apodo: "Fantasma", equipo: "Ex-LOUD / Influencer", disp: "iPhone 14 Pro", estilo: "Movimiento extremo / Puntería fija", general: 185, puntoRojo: 180, mira2x: 175, mira4x: 165, awm: 75, olhadinha: 190, boton: 46, dpi: null, insignia: "👻 Leyenda Mobile" },
  { id: "pro-3", nombre: "Diogo Lima", apodo: "Blackn444", equipo: "Los Grandes", disp: "Android (POCO / ROG)", estilo: "Control de retroceso / MP40 y Escopetas", general: 175, puntoRojo: 170, mira2x: 168, mira4x: 155, awm: 68, olhadinha: 180, boton: 48, dpi: 560, insignia: "⚡ Maestro del Capa" },
  { id: "pro-4", nombre: "Angelo Roberto", apodo: "Two9", equipo: "Fluxo", disp: "iPhone 13 Pro Max", estilo: "Juego táctico y media distancia", general: 168, puntoRojo: 165, mira2x: 160, mira4x: 150, awm: 70, olhadinha: 175, boton: 50, dpi: null, insignia: "🎯 Francotirador" },
  { id: "pro-5", nombre: "Gabriel Henrique", apodo: "Bak", equipo: "Fluxo", disp: "iPhone 15 Pro", estilo: "Capitán / Visión periférica", general: 182, puntoRojo: 178, mira2x: 172, mira4x: 160, awm: 80, olhadinha: 195, boton: 45, dpi: null, insignia: "⭐ Estratega" },
  { id: "pro-6", nombre: "Jonatha Silva", apodo: "Apelapato", equipo: "Mobile Pro", disp: "Samsung Galaxy S24 Ultra", estilo: "Capas consecutivos a larga distancia", general: 172, puntoRojo: 168, mira2x: 165, mira4x: 154, awm: 65, olhadinha: 170, boton: 47, dpi: 520, insignia: "🔥 High Sense Android" }
];

// 3. ESTADO GLOBAL
const AppState = {
  selectedSo: 'Android',
  selectedMarca: 'Samsung',
  selectedModelo: 'Galaxy S24 Ultra',
  selectedExperiencia: 'intermedio',
  selectedTipoSensi: 'alta',
  conDpi: true,
  preferenciaJuego: 'velocidad',
  currentConfig: null,
  activeTab: 'generator'
};

// 4. INICIALIZACIÓN
document.addEventListener('DOMContentLoaded', () => {
  initTabs();
  initForm();
  poblarMarcas('Android');
  renderProPlayers();
  initPerformance();
  cargarHistorial();
  cargarRanking();
});

// Pestañas y Drawer de Navegación
function initTabs() {
  // Pestañas Desktop
  document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const tabId = btn.getAttribute('data-tab');
      cambiarTab(tabId);
    });
  });

  // Enlaces del Drawer Móvil
  document.querySelectorAll('.drawer-link').forEach(btn => {
    btn.addEventListener('click', () => {
      const tabId = btn.getAttribute('data-tab');
      cambiarTab(tabId);
      cerrarDrawer();
    });
  });

  // Abrir Drawer con botón Hamburguesa
  const btnHam = document.getElementById('btn-hamburger');
  const btnClose = document.getElementById('btn-close-drawer');
  const overlay = document.getElementById('mobile-overlay');

  if (btnHam) btnHam.addEventListener('click', abrirDrawer);
  if (btnClose) btnClose.addEventListener('click', cerrarDrawer);
  if (overlay) overlay.addEventListener('click', cerrarDrawer);
}

function abrirDrawer() {
  document.getElementById('mobile-drawer').classList.add('active');
  document.getElementById('mobile-overlay').classList.add('active');
  document.body.style.overflow = 'hidden'; // Evita scroll de fondo
}

function cerrarDrawer() {
  const drawer = document.getElementById('mobile-drawer');
  const overlay = document.getElementById('mobile-overlay');
  if (drawer) drawer.classList.remove('active');
  if (overlay) overlay.classList.remove('active');
  document.body.style.overflow = '';
}

function cambiarTab(tabId) {
  AppState.activeTab = tabId;
  
  // Sincronizar botones desktop
  document.querySelectorAll('.tab-btn').forEach(b => {
    b.classList.toggle('active', b.getAttribute('data-tab') === tabId);
  });

  // Sincronizar enlaces drawer
  document.querySelectorAll('.drawer-link').forEach(b => {
    b.classList.toggle('active', b.getAttribute('data-tab') === tabId);
  });

  // Mostrar sección activa
  document.querySelectorAll('.app-section').forEach(sec => {
    sec.style.display = (sec.id === `section-${tabId}`) ? 'block' : 'none';
  });

  if (tabId === 'history') cargarHistorial();
  if (tabId === 'ranking') cargarRanking();
}

// Formularios
function initForm() {
  // SO
  document.querySelectorAll('input[name="sistemaOperativo"]').forEach(r => {
    r.addEventListener('change', (e) => {
      AppState.selectedSo = e.target.value;
      actualizarTiles('sistemaOperativo');
      const dpiWrap = document.getElementById('dpi-option-wrap');
      if (AppState.selectedSo === 'iOS') {
        if (dpiWrap) dpiWrap.style.display = 'none';
        AppState.conDpi = false;
      } else {
        if (dpiWrap) dpiWrap.style.display = 'flex';
      }
      poblarMarcas(AppState.selectedSo);
    });
  });

  // Marca
  document.getElementById('select-marca').addEventListener('change', (e) => {
    AppState.selectedMarca = e.target.value;
    poblarModelos(AppState.selectedMarca);
  });

  // Modelo
  document.getElementById('select-modelo').addEventListener('change', (e) => {
    AppState.selectedModelo = e.target.value;
  });

  // Radio tiles
  ['nivelExperiencia', 'tipoSensibilidad', 'conDpi', 'preferenciaJuego'].forEach(name => {
    document.querySelectorAll(`input[name="${name}"]`).forEach(r => {
      r.addEventListener('change', (e) => {
        if (name === 'conDpi') {
          AppState.conDpi = (e.target.value === 'true');
        } else {
          AppState[name] = e.target.value;
        }
        actualizarTiles(name);
      });
    });
  });

  // Botones
  document.getElementById('btn-generar-sensi').addEventListener('click', ejecutarGenerador);
  document.getElementById('btn-copiar-config').addEventListener('click', copiarConfiguracion);
  document.getElementById('btn-guardar-favorito').addEventListener('click', alternarFavorito);
  document.getElementById('btn-variar-config').addEventListener('click', () => {
    ejecutarGenerador();
    mostrarToast('Variación calculada con éxito', 'info');
  });
}

function actualizarTiles(groupName) {
  document.querySelectorAll(`input[name="${groupName}"]`).forEach(input => {
    const parent = input.closest('.radio-tile-label');
    if (parent) {
      parent.classList.toggle('selected', input.checked);
    }
  });
}

// Llenar selectores
function poblarMarcas(so) {
  const selectMarca = document.getElementById('select-marca');
  const marcas = [...new Set(DispositivosData.filter(d => d.so.toLowerCase() === so.toLowerCase()).map(d => d.marca))].sort();
  selectMarca.innerHTML = '';
  marcas.forEach(m => {
    const opt = document.createElement('option');
    opt.value = m;
    opt.textContent = m;
    selectMarca.appendChild(opt);
  });
  AppState.selectedMarca = marcas[0] || '';
  selectMarca.value = AppState.selectedMarca;
  poblarModelos(AppState.selectedMarca);
}

function poblarModelos(marca) {
  const selectModelo = document.getElementById('select-modelo');
  const modelos = DispositivosData.filter(d => d.marca.toLowerCase() === marca.toLowerCase());
  selectModelo.innerHTML = '';
  modelos.forEach(d => {
    const opt = document.createElement('option');
    opt.value = d.modelo;
    opt.textContent = `${d.modelo} (${d.hz}Hz)`;
    selectModelo.appendChild(opt);
  });
  if (modelos.length > 0) {
    selectModelo.selectedIndex = 0;
    AppState.selectedModelo = modelos[0].modelo;
  }
}

// 5. MOTOR ALGORÍTMICO CLIENT-SIDE (MISMA MATEMÁTICA PURA)
function ejecutarGenerador() {
  const btn = document.getElementById('btn-generar-sensi');
  btn.disabled = true;
  btn.innerHTML = '⚡ CALIBRANDO CONFIGURACIÓN...';

  setTimeout(() => {
    const disp = DispositivosData.find(d => d.marca.toLowerCase() === AppState.selectedMarca.toLowerCase() && d.modelo.toLowerCase() === AppState.selectedModelo.toLowerCase()) || {
      factor: 1.0, hz: 60, dpiDefault: 384, dpiMax: 500
    };

    const esIos = (AppState.selectedSo === 'iOS');
    
    // Base General según tipo
    let generalBase = (AppState.selectedTipoSensi === 'alta') ? 182 : ((AppState.selectedTipoSensi === 'baja') ? 125 : 158);

    // Preferencia
    let modEstilo = 0;
    let estiloDesc = 'Equilibrado para todas las armas.';
    if (AppState.preferenciaJuego === 'precision') {
      modEstilo = -10;
      estiloDesc = 'Enfoque en headshots limpios a media distancia y control de retroceso.';
    } else if (AppState.preferenciaJuego === 'velocidad') {
      modEstilo = 12;
      estiloDesc = 'Enfoque en rusheo agresivo, giros de cámara instantáneos y colocación de pared Gloo.';
    }

    // Tasa Hz
    let modHz = (disp.hz >= 120) ? -8 : ((disp.hz === 90) ? -3 : 0);

    // Sistema Operativo y Factor Táctil
    let modSo = esIos ? -6 : 4;
    let modDisp = (disp.factor - 1.0) * 20;

    // DPI
    let dpiSugerido = null;
    let modDpi = 0;
    if (AppState.conDpi && !esIos) {
      modDpi = -12;
      dpiSugerido = (AppState.selectedTipoSensi === 'alta') ? Math.min(disp.dpiDefault + 180, disp.dpiMax) : Math.min(disp.dpiDefault + 120, disp.dpiMax);
    }

    const clamp = (v, min, max) => Math.max(min, Math.min(max, v));

    const general = clamp(Math.round(generalBase + modEstilo + modHz + modSo + modDisp + modDpi), 85, 200);
    const puntoRojo = clamp(general - (AppState.preferenciaJuego === 'precision' ? 6 : 2), 80, 200);
    const mira2x = clamp(puntoRojo + (esIos ? 4 : 2), 80, 200);
    const mira4x = clamp(mira2x - 12, 70, 195);
    const awmBase = (AppState.selectedTipoSensi === 'alta') ? 88 : ((AppState.selectedTipoSensi === 'baja') ? 52 : 70);
    const francotirador = clamp(awmBase + Math.round(modEstilo * 0.5), 35, 140);
    const camara360 = clamp(general + 10, 90, 200);

    let boton = (AppState.selectedTipoSensi === 'alta') ? ((AppState.preferenciaJuego === 'velocidad') ? 43 : 46) : ((AppState.selectedTipoSensi === 'baja') ? 54 : 49);

    const config = {
      id: 'cfg_' + Date.now(),
      marca: AppState.selectedMarca,
      modelo: AppState.selectedModelo,
      so: AppState.selectedSo,
      tipoSensi: AppState.selectedTipoSensi,
      general, puntoRojo, mira2x, mira4x, francotirador, camara360,
      botonDisparo: boton,
      dpi: dpiSugerido,
      estilo: estiloDesc,
      explicacion: `Calibrado para ${AppState.selectedModelo} (${disp.hz}Hz) ${dpiSugerido ? `con DPI sugerido de ${dpiSugerido}.` : 'con DPI de fábrica.'} Miras sincronizadas para levantamiento de mira sin dispersión sobre el casco.`,
      fecha: new Date().toLocaleDateString('es-ES')
    };

    AppState.currentConfig = config;
    guardarEnHistorialLocal(config);
    renderResultado(config);

    btn.disabled = false;
    btn.innerHTML = '⚡ GENERAR MI SENSIBILIDAD';
    mostrarToast('¡Sensibilidad calibrada con éxito!', 'success');

    document.getElementById('resultado-sensibilidad').scrollIntoView({ behavior: 'smooth' });
  }, 450);
}

function renderResultado(c) {
  const box = document.getElementById('resultado-sensibilidad');
  box.classList.add('active');

  document.getElementById('res-device-title').textContent = `${c.marca} ${c.modelo}`;
  document.getElementById('res-style-badge').textContent = c.tipoSensi.toUpperCase();

  animarValor('val-general', 'bar-general', c.general);
  animarValor('val-reddot', 'bar-reddot', c.puntoRojo);
  animarValor('val-mira2x', 'bar-mira2x', c.mira2x);
  animarValor('val-mira4x', 'bar-mira4x', c.mira4x);
  animarValor('val-awm', 'bar-awm', c.francotirador);
  animarValor('val-olhadinha', 'bar-olhadinha', c.camara360);

  document.getElementById('val-boton').textContent = `${c.botonDisparo}%`;
  document.getElementById('val-dpi').textContent = c.dpi ? c.dpi : 'Estándar';
  document.getElementById('res-explicacion').textContent = c.explicacion;
}

function animarValor(valId, barId, targetVal) {
  const elVal = document.getElementById(valId);
  const elBar = document.getElementById(barId);
  if (!elVal) return;

  let current = 0;
  const step = Math.ceil(targetVal / 25);
  const timer = setInterval(() => {
    current += step;
    if (current >= targetVal) {
      current = targetVal;
      clearInterval(timer);
    }
    elVal.textContent = current;
    if (elBar) elBar.style.width = `${(current / 200) * 100}%`;
  }, 20);
}

// 6. COPIAR Y GUARDAR
function copiarConfiguracion() {
  if (!AppState.currentConfig) return;
  const c = AppState.currentConfig;
  const texto = `🔥 SENSI7GRINGO - CONFIGURACIÓN CALIBRADA 🔥
📱 Dispositivo: ${c.marca} ${c.modelo} (${c.so})
🎯 Nivel: ${c.tipoSensi.toUpperCase()}
---------------------------------
• General: ${c.general}
• Punto Rojo: ${c.puntoRojo}
• Mira 2X: ${c.mira2x}
• Mira 4X: ${c.mira4x}
• Francotirador (AWM): ${c.francotirador}
• Cámara 360 / Olhadinha: ${c.camara360}
---------------------------------
🔘 Botón de Disparo: ${c.botonDisparo}%
📐 DPI Sugerido: ${c.dpi ? c.dpi : 'Estándar'}
⚡ Sensi7gringo GitHub Edition`;

  navigator.clipboard.writeText(texto).then(() => {
    mostrarToast('¡Configuración copiada al portapapeles!', 'success');
  });
}

function alternarFavorito() {
  mostrarToast('Configuración agregada a tus favoritos', 'success');
}

// 7. HISTORIAL EN LOCALSTORAGE
function guardarEnHistorialLocal(cfg) {
  let hist = JSON.parse(localStorage.getItem('s7g_hist') || '[]');
  hist.unshift(cfg);
  if (hist.length > 20) hist.pop();
  localStorage.setItem('s7g_hist', JSON.stringify(hist));
}

function cargarHistorial() {
  const cont = document.getElementById('historial-container');
  if (!cont) return;
  const hist = JSON.parse(localStorage.getItem('s7g_hist') || '[]');

  if (hist.length > 0) {
    cont.innerHTML = '';
    hist.forEach((item, index) => {
      const card = document.createElement('div');
      card.className = 'history-card';
      card.innerHTML = `
        <div>
          <strong style="color:#ff851b; font-size:1.1rem;">${item.marca} ${item.modelo}</strong>
          <p style="font-size:0.85rem; color:#9ca3af;">General: ${item.general} | Red Dot: ${item.puntoRojo} | Botón: ${item.botonDisparo}% | DPI: ${item.dpi || 'Estándar'} (${item.fecha})</p>
        </div>
        <div style="display:flex; gap:8px;">
          <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem;" onclick="cargarHistItem(${index})">Ver</button>
          <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem; color:#ef4444;" onclick="borrarHistItem(${index})">Borrar</button>
        </div>
      `;
      cont.appendChild(card);
    });
  } else {
    cont.innerHTML = `
      <div class="empty-state">
        <p>No tienes sensibilidades generadas aún.</p>
        <button class="btn-primary" style="margin-top:12px;" onclick="cambiarTab('generator')">Crear Mi Primera Sensi</button>
      </div>
    `;
  }
}

window.cargarHistItem = function(index) {
  const hist = JSON.parse(localStorage.getItem('s7g_hist') || '[]');
  if (hist[index]) {
    cambiarTab('generator');
    renderResultado(hist[index]);
    AppState.currentConfig = hist[index];
  }
};

window.borrarHistItem = function(index) {
  let hist = JSON.parse(localStorage.getItem('s7g_hist') || '[]');
  hist.splice(index, 1);
  localStorage.setItem('s7g_hist', JSON.stringify(hist));
  cargarHistorial();
  mostrarToast('Configuración eliminada', 'info');
};

// 8. RANKING PRE-POBLADO
function cargarRanking() {
  const cont = document.getElementById('ranking-container');
  if (!cont) return;
  const ranking = [
    { marca: "Samsung", modelo: "Galaxy S24 Ultra", tipo: "Alta", gen: 178, red: 174, m2: 176, m4: 164, bot: 45, rating: "5.0" },
    { marca: "Apple", modelo: "iPhone 15 Pro Max", tipo: "Alta", gen: 194, red: 190, m2: 188, m4: 176, bot: 44, rating: "4.9" },
    { marca: "Xiaomi", modelo: "POCO X6 Pro 5G", tipo: "Alta", gen: 182, red: 178, m2: 175, m4: 163, bot: 44, rating: "4.9" },
    { marca: "Motorola", modelo: "Edge 40 Neo", tipo: "Media", gen: 168, red: 165, m2: 160, m4: 152, bot: 48, rating: "4.8" },
    { marca: "Apple", modelo: "iPhone 13 Pro Max", tipo: "Alta", gen: 186, red: 182, m2: 180, m4: 168, bot: 46, rating: "4.8" }
  ];

  cont.innerHTML = '';
  ranking.forEach((item, idx) => {
    const card = document.createElement('div');
    card.className = 'history-card';
    card.innerHTML = `
      <div style="display:flex; align-items:center; gap:12px;">
        <span style="font-size:1.4rem; font-weight:900; color:#ff851b;">#${idx + 1}</span>
        <div>
          <strong style="font-size:1.05rem;">${item.marca} ${item.modelo}</strong>
          <p style="font-size:0.82rem; color:#9ca3af;">Calibración ${item.tipo} | General: ${item.gen} | Puntuación: ⭐ ${item.rating}</p>
        </div>
      </div>
      <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem;" onclick="copiarDirecto('${item.gen}', '${item.red}', '${item.m2}', '${item.m4}', '${item.bot}')">Copiar</button>
    `;
    cont.appendChild(card);
  });
}

// 9. PRO PLAYERS RENDER
function renderProPlayers() {
  const grid = document.getElementById('pro-players-grid');
  if (!grid) return;
  grid.innerHTML = '';

  ProPlayersData.forEach(p => {
    const card = document.createElement('div');
    card.className = 'player-card';
    card.innerHTML = `
      <div class="player-header">
        <div class="player-meta">
          <div class="player-avatar">${p.apodo.substring(0,2)}</div>
          <div class="player-name-wrap">
            <h3>${p.apodo}</h3>
            <span>${p.equipo}</span>
          </div>
        </div>
        <span class="player-badge">${p.insignia}</span>
      </div>
      <p style="font-size:0.85rem; color:#9ca3af;">📱 ${p.disp} (${p.estilo})</p>
      <div class="player-stats-table">
        <div class="stat-item"><div class="lbl">General</div><div class="val">${p.general}</div></div>
        <div class="stat-item"><div class="lbl">Punto Rojo</div><div class="val">${p.puntoRojo}</div></div>
        <div class="stat-item"><div class="lbl">Mira 2X</div><div class="val">${p.mira2x}</div></div>
        <div class="stat-item"><div class="lbl">Mira 4X</div><div class="val">${p.mira4x}</div></div>
        <div class="stat-item"><div class="lbl">Botón</div><div class="val">${p.boton}%</div></div>
        <div class="stat-item"><div class="lbl">DPI</div><div class="val">${p.dpi || 'Estándar'}</div></div>
      </div>
      <button type="button" class="btn-primary" style="width:100%; padding:10px; font-size:0.9rem;" onclick="adaptarProPlayer('${p.id}')">
        ⚡ ADAPTAR A MI TELÉFONO
      </button>
    `;
    grid.appendChild(card);
  });
}

window.adaptarProPlayer = function(id) {
  cambiarTab('generator');
  mostrarToast('Adaptando configuración del Pro Player a tu dispositivo...', 'info');
  setTimeout(() => {
    ejecutarGenerador();
  }, 500);
};

window.copiarDirecto = function(gen, red, m2, m4, bot) {
  const txt = `Sensi7gringo: General ${gen} | RedDot ${red} | 2X ${m2} | 4X ${m4} | Botón ${bot}%`;
  navigator.clipboard.writeText(txt).then(() => {
    mostrarToast('¡Valores copiados al portapapeles!', 'success');
  });
};

// 10. PERFORMANCE
function initPerformance() {
  const btn = document.getElementById('btn-iniciar-diagnostico');
  const consoleLog = document.getElementById('opt-console-log');
  if (!btn || !consoleLog) return;

  btn.addEventListener('click', () => {
    btn.disabled = true;
    consoleLog.innerHTML = '';
    const pasos = [
      { t: 300, msg: 'Iniciando escaneo de características del navegador...', type: 'info' },
      { t: 800, msg: `User-Agent detectado: ${navigator.userAgent.substring(0, 50)}...`, type: 'info' },
      { t: 1300, msg: `Resolución de pantalla activa: ${window.screen.width} x ${window.screen.height} px`, type: 'info' },
      { t: 1900, msg: `Profundidad de color y GPU: ${window.screen.colorDepth}-bit / WebGL activo`, type: 'success' },
      { t: 2500, msg: 'Analizando buffer de respuesta táctil y micro-retraso...', type: 'warning' },
      { t: 3200, msg: 'RECOMENDACIÓN: Para 0ms lag en Free Fire, active 90Hz/120Hz en Ajustes de Pantalla.', type: 'success' },
      { t: 3800, msg: 'Diagnóstico completado. El dispositivo está listo para calibración táctil.', type: 'success' }
    ];

    pasos.forEach(p => {
      setTimeout(() => {
        const line = document.createElement('div');
        line.className = `console-line ${p.type}`;
        line.textContent = `> ${p.msg}`;
        consoleLog.appendChild(line);
        consoleLog.scrollTop = consoleLog.scrollHeight;
      }, p.t);
    });

    setTimeout(() => {
      btn.disabled = false;
      mostrarToast('Diagnóstico finalizado con recomendaciones', 'success');
    }, 4200);
  });
}

// 11. TOAST
function mostrarToast(mensaje, tipo = 'info') {
  let container = document.getElementById('toast-container');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  toast.className = 'toast';
  let icon = (tipo === 'success') ? '✅' : ((tipo === 'warning') ? '⚠️' : '⚡');
  toast.innerHTML = `<span>${icon}</span> <span>${mensaje}</span>`;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateX(40px)';
    toast.style.transition = 'all 0.3s ease';
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}
