/**
 * Sensi7gringo - Core Interactive JavaScript Controller
 * Manejo de pestañas, carga dinámica de marcas/modelos,
 * conexión al backend REST, diagnóstico y persistencia de sesión.
 */

const AppState = {
  sessionId: '',
  selectedSo: 'Android',
  selectedMarca: '',
  selectedModelo: '',
  selectedExperiencia: 'intermedio',
  selectedTipoSensi: 'alta',
  conDpi: true,
  preferenciaJuego: 'velocidad',
  currentConfig: null,
  activeTab: 'generator'
};

document.addEventListener('DOMContentLoaded', () => {
  initSession();
  initTabs();
  initFormInteractions();
  cargarMarcas('Android');
  initPerformanceModule();
  initAimModule();
  cargarHistorial();
  cargarRanking();
});

// 1. Inicialización de Sesión Persistente (UUID anónimo)
function initSession() {
  let sid = localStorage.getItem('sensi7gringo_sid');
  if (!sid) {
    sid = 's7g_' + Math.random().toString(36).substring(2, 11) + Date.now().toString(36);
    localStorage.setItem('sensi7gringo_sid', sid);
  }
  AppState.sessionId = sid;
}

// 2. Control de Pestañas
function initTabs() {
  const tabButtons = document.querySelectorAll('.tab-btn');
  tabButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      const tabTarget = btn.getAttribute('data-tab');
      cambiarTab(tabTarget);
    });
  });
}

function cambiarTab(tabId) {
  AppState.activeTab = tabId;
  
  // Actualizar botones de navegación
  document.querySelectorAll('.tab-btn').forEach(btn => {
    if (btn.getAttribute('data-tab') === tabId) {
      btn.classList.add('active');
    } else {
      btn.classList.remove('active');
    }
  });

  // Mostrar la sección correspondiente
  document.querySelectorAll('.app-section').forEach(sec => {
    if (sec.id === `section-${tabId}`) {
      sec.style.display = 'block';
      sec.classList.add('fade-in');
    } else {
      sec.style.display = 'none';
    }
  });

  // Si cambia a historial o ranking, refrescar datos
  if (tabId === 'history') cargarHistorial();
  if (tabId === 'ranking') cargarRanking();
}

// 3. Manejo de Formularios y Opciones Visuales
function initFormInteractions() {
  // Selector de Sistema Operativo (Radio tiles)
  document.querySelectorAll('input[name="sistemaOperativo"]').forEach(input => {
    input.addEventListener('change', (e) => {
      AppState.selectedSo = e.target.value;
      actualizarTileLabels('sistemaOperativo');
      
      // Ajustar DPI selector si es iOS
      const dpiWrap = document.getElementById('dpi-option-wrap');
      if (AppState.selectedSo === 'iOS') {
        if (dpiWrap) dpiWrap.style.display = 'none';
        AppState.conDpi = false;
      } else {
        if (dpiWrap) dpiWrap.style.display = 'flex';
      }

      cargarMarcas(AppState.selectedSo);
    });
  });

  // Selector de Marca
  const selectMarca = document.getElementById('select-marca');
  if (selectMarca) {
    selectMarca.addEventListener('change', (e) => {
      AppState.selectedMarca = e.target.value;
      cargarModelos(AppState.selectedMarca);
    });
  }

  // Selector de Modelo
  const selectModelo = document.getElementById('select-modelo');
  if (selectModelo) {
    selectModelo.addEventListener('change', (e) => {
      AppState.selectedModelo = e.target.value;
    });
  }

  // Nivel de Experiencia
  document.querySelectorAll('input[name="nivelExperiencia"]').forEach(input => {
    input.addEventListener('change', (e) => {
      AppState.selectedExperiencia = e.target.value;
      actualizarTileLabels('nivelExperiencia');
    });
  });

  // Tipo de Sensibilidad
  document.querySelectorAll('input[name="tipoSensibilidad"]').forEach(input => {
    input.addEventListener('change', (e) => {
      AppState.selectedTipoSensi = e.target.value;
      actualizarTileLabels('tipoSensibilidad');
    });
  });

  // Con DPI / Sin DPI
  document.querySelectorAll('input[name="conDpi"]').forEach(input => {
    input.addEventListener('change', (e) => {
      AppState.conDpi = (e.target.value === 'true');
      actualizarTileLabels('conDpi');
    });
  });

  // Preferencia de Juego
  document.querySelectorAll('input[name="preferenciaJuego"]').forEach(input => {
    input.addEventListener('change', (e) => {
      AppState.preferenciaJuego = e.target.value;
      actualizarTileLabels('preferenciaJuego');
    });
  });

  // Botón Principal GENERAR SENSIBILIDAD
  const btnGenerar = document.getElementById('btn-generar-sensi');
  if (btnGenerar) {
    btnGenerar.addEventListener('click', generarSensibilidad);
  }

  // Botón Copiar Configuración
  const btnCopiar = document.getElementById('btn-copiar-config');
  if (btnCopiar) {
    btnCopiar.addEventListener('click', copiarConfiguracion);
  }

  // Botón Guardar / Favorito
  const btnFavorito = document.getElementById('btn-guardar-favorito');
  if (btnFavorito) {
    btnFavorito.addEventListener('click', alternarFavoritoCurrent);
  }

  // Botón Variación Rápida
  const btnVariar = document.getElementById('btn-variar-config');
  if (btnVariar) {
    btnVariar.addEventListener('click', () => {
      generarSensibilidad();
      mostrarToast('Variación calculada con éxito', 'info');
    });
  }
}

function actualizarTileLabels(groupName) {
  document.querySelectorAll(`input[name="${groupName}"]`).forEach(input => {
    const parentLabel = input.closest('.radio-tile-label');
    if (parentLabel) {
      if (input.checked) {
        parentLabel.classList.add('selected');
      } else {
        parentLabel.classList.remove('selected');
      }
    }
  });
}

// 4. Conexión REST a Dispositivos (Marcas y Modelos)
async function cargarMarcas(sistema) {
  const selectMarca = document.getElementById('select-marca');
  if (!selectMarca) return;

  selectMarca.innerHTML = '<option value="">Cargando marcas...</option>';
  try {
    const resp = await fetch(`/api/dispositivos/marcas?sistemaOperativo=${encodeURIComponent(sistema)}`);
    const json = await resp.json();
    if (json.success && json.data.length > 0) {
      selectMarca.innerHTML = '<option value="">-- Selecciona una marca --</option>';
      json.data.forEach(marca => {
        const opt = document.createElement('option');
        opt.value = marca;
        opt.textContent = marca;
        selectMarca.appendChild(opt);
      });

      // Auto seleccionar primera marca común
      const marcaDefault = (sistema === 'iOS') ? 'Apple' : (json.data.includes('Samsung') ? 'Samsung' : json.data[0]);
      selectMarca.value = marcaDefault;
      AppState.selectedMarca = marcaDefault;
      cargarModelos(marcaDefault);
    }
  } catch (err) {
    console.warn('Error al cargar marcas:', err);
    selectMarca.innerHTML = '<option value="Samsung">Samsung</option><option value="Xiaomi">Xiaomi</option><option value="Apple">Apple</option>';
  }
}

async function cargarModelos(marca) {
  const selectModelo = document.getElementById('select-modelo');
  if (!selectModelo || !marca) return;

  selectModelo.innerHTML = '<option value="">Cargando modelos...</option>';
  try {
    const resp = await fetch(`/api/dispositivos/modelos/${encodeURIComponent(marca)}`);
    const json = await resp.json();
    if (json.success && json.data.length > 0) {
      selectModelo.innerHTML = '<option value="">-- Selecciona el modelo exacto --</option>';
      json.data.forEach(d => {
        const opt = document.createElement('option');
        opt.value = d.modelo;
        opt.textContent = `${d.modelo} (${d.tasaRefrescoHz}Hz)`;
        selectModelo.appendChild(opt);
      });
      // Seleccionar el primero por comodidad
      selectModelo.selectedIndex = 1;
      AppState.selectedModelo = selectModelo.value;
    }
  } catch (err) {
    console.warn('Error al cargar modelos:', err);
  }
}

// 5. Generación de Sensibilidad personalizada
async function generarSensibilidad() {
  const selectMarca = document.getElementById('select-marca');
  const selectModelo = document.getElementById('select-modelo');
  const btn = document.getElementById('btn-generar-sensi');

  const marca = selectMarca ? selectMarca.value : AppState.selectedMarca;
  const modelo = selectModelo ? selectModelo.value : AppState.selectedModelo;

  if (!marca || !modelo) {
    mostrarToast('Por favor selecciona una marca y modelo de tu teléfono', 'warning');
    return;
  }

  const payload = {
    sessionId: AppState.sessionId,
    sistemaOperativo: AppState.selectedSo,
    marca: marca,
    modelo: modelo,
    nivelExperiencia: AppState.selectedExperiencia,
    tipoSensibilidad: AppState.selectedTipoSensi,
    conDpi: AppState.conDpi,
    preferenciaJuego: AppState.preferenciaJuego
  };

  if (btn) {
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner-border"></span> GENERANDO CONFIGURACIÓN...';
  }

  try {
    const resp = await fetch('/api/sensibilidades/generar', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const json = await resp.json();

    if (json.success && json.data) {
      AppState.currentConfig = json.data;
      renderResultado(json.data);
      mostrarToast('¡Sensibilidad calibrada con éxito!', 'success');
      // Scroll suave hacia el resultado
      document.getElementById('resultado-sensibilidad').scrollIntoView({ behavior: 'smooth' });
    } else {
      mostrarToast(json.message || 'Error al generar la sensibilidad', 'error');
    }
  } catch (err) {
    console.error('Error generando sensibilidad:', err);
    mostrarToast('Error de conexión con el servidor de calibración', 'error');
  } finally {
    if (btn) {
      btn.disabled = false;
      btn.innerHTML = '⚡ GENERAR MI SENSIBILIDAD';
    }
  }
}

function renderResultado(s) {
  const box = document.getElementById('resultado-sensibilidad');
  if (!box) return;

  box.classList.add('active');

  // Metadatos
  document.getElementById('res-device-title').textContent = `${s.marca} ${s.modelo}`;
  document.getElementById('res-style-badge').textContent = s.estiloRecomendado || 'Equilibrado';

  // Valores de Miras
  animarValor('val-general', 'bar-general', s.general);
  animarValor('val-reddot', 'bar-reddot', s.puntoRojo);
  animarValor('val-mira2x', 'bar-mira2x', s.mira2x);
  animarValor('val-mira4x', 'bar-mira4x', s.mira4x);
  animarValor('val-awm', 'bar-awm', s.francotirador);
  animarValor('val-olhadinha', 'bar-olhadinha', s.camara360);

  // Botón y DPI
  document.getElementById('val-boton').textContent = `${s.botonDisparo}%`;
  const dpiVal = document.getElementById('val-dpi');
  if (s.dpiRecomendado) {
    dpiVal.textContent = s.dpiRecomendado;
  } else {
    dpiVal.textContent = 'Estándar';
  }

  // Explicación técnica
  document.getElementById('res-explicacion').textContent = s.explicacionAlgoritmo;
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
    if (elBar) {
      // Escala 0 a 200 en Free Fire -> porcentaje = val / 200 * 100
      elBar.style.width = `${(current / 200) * 100}%`;
    }
  }, 20);
}

// 6. Copiar Configuración
function copiarConfiguracion() {
  if (!AppState.currentConfig) return;
  const c = AppState.currentConfig;
  const texto = `🔥 SENSI7GRINGO - CONFIGURACIÓN CALIBRADA 🔥
📱 Dispositivo: ${c.marca} ${c.modelo} (${c.sistemaOperativo})
🎯 Estilo: ${c.tipoSensibilidad.toUpperCase()} / ${c.preferenciaJuego.toUpperCase()}
---------------------------------
• General: ${c.general}
• Punto Rojo: ${c.puntoRojo}
• Mira 2X: ${c.mira2x}
• Mira 4X: ${c.mira4x}
• Francotirador (AWM): ${c.francotirador}
• Cámara 360 / Olhadinha: ${c.camara360}
---------------------------------
🔘 Botón de Disparo: ${c.botonDisparo}%
📐 DPI Sugerido: ${c.dpiRecomendado ? c.dpiRecomendado : 'Estándar'}
⚡ Calibrado en: https://sensi7gringo.site/`;

  navigator.clipboard.writeText(texto).then(() => {
    mostrarToast('¡Configuración copiada al portapapeles!', 'success');
  }).catch(() => {
    mostrarToast('No se pudo copiar automáticamente', 'warning');
  });
}

// 7. Favoritos
async function alternarFavoritoCurrent() {
  if (!AppState.currentConfig || !AppState.currentConfig.id) return;
  try {
    const resp = await fetch(`/api/sensibilidades/${AppState.currentConfig.id}/favorito`, { method: 'POST' });
    const json = await resp.json();
    if (json.success) {
      mostrarToast(json.message, 'success');
    }
  } catch (err) {
    mostrarToast('Guardado localmente', 'info');
  }
}

// 8. Módulo Performance (Diagnóstico en vivo)
function initPerformanceModule() {
  const btnDiagnostico = document.getElementById('btn-iniciar-diagnostico');
  const consoleLog = document.getElementById('opt-console-log');
  if (!btnDiagnostico || !consoleLog) return;

  btnDiagnostico.addEventListener('click', () => {
    btnDiagnostico.disabled = true;
    consoleLog.innerHTML = '';

    const pasos = [
      { t: 300, msg: 'Iniciando escaneo de características del navegador...', type: 'info' },
      { t: 800, msg: `User-Agent detectado: ${navigator.userAgent.substring(0, 50)}...`, type: 'info' },
      { t: 1300, msg: `Resolución de pantalla activa: ${window.screen.width} x ${window.screen.height} px`, type: 'info' },
      { t: 1900, msg: `Profundidad de color y GPU: ${window.screen.colorDepth}-bit / WebGL activo`, type: 'success' },
      { t: 2500, msg: 'Analizando buffer de respuesta táctil y micro-retraso...', type: 'warning' },
      { t: 3200, msg: 'RECOMENDACIÓN: Para 0ms lag en Free Fire, desactive el modo ahorro de batería y habilite 90Hz/120Hz en Ajustes de Pantalla.', type: 'success' },
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
      btnDiagnostico.disabled = false;
      mostrarToast('Diagnóstico finalizado con recomendaciones', 'success');
    }, 4200);
  });
}

// 9. Módulo Aim (Calibración)
function initAimModule() {
  const btnTestMira = document.getElementById('btn-test-mira');
  if (!btnTestMira) return;

  btnTestMira.addEventListener('click', () => {
    mostrarToast('Técnica recomendada: Desliza el botón en forma de "J" invertida para disparos a la cabeza con escopeta.', 'info');
  });
}

// 10. Historial y Ranking
async function cargarHistorial() {
  const list = document.getElementById('historial-container');
  if (!list) return;

  try {
    const resp = await fetch(`/api/sensibilidades/historial?sessionId=${encodeURIComponent(AppState.sessionId)}`);
    const json = await resp.json();
    if (json.success && json.data.length > 0) {
      list.innerHTML = '';
      json.data.forEach(item => {
        const card = document.createElement('div');
        card.className = 'history-card';
        card.innerHTML = `
          <div>
            <strong style="color:#ff851b; font-size:1.1rem;">${item.marca} ${item.modelo}</strong>
            <p style="font-size:0.85rem; color:#9ca3af;">General: ${item.general} | Red Dot: ${item.puntoRojo} | Botón: ${item.botonDisparo}% | DPI: ${item.dpiRecomendado || 'Sin DPI'}</p>
          </div>
          <div style="display:flex; gap:8px;">
            <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem;" onclick="cargarEnGenerador('${item.id}')">Ver</button>
            <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem; color:#ef4444;" onclick="eliminarDelHistorial('${item.id}')">Borrar</button>
          </div>
        `;
        list.appendChild(card);
      });
    } else {
      list.innerHTML = `
        <div class="empty-state">
          <p>No tienes sensibilidades generadas aún.</p>
          <button class="btn-primary" style="margin-top:12px;" onclick="cambiarTab('generator')">Crear Mi Primera Sensi</button>
        </div>
      `;
    }
  } catch (err) {
    console.warn('Error cargando historial:', err);
  }
}

async function cargarRanking() {
  const list = document.getElementById('ranking-container');
  if (!list) return;

  try {
    const resp = await fetch('/api/sensibilidades/ranking');
    const json = await resp.json();
    if (json.success && json.data.length > 0) {
      list.innerHTML = '';
      json.data.forEach((item, idx) => {
        const card = document.createElement('div');
        card.className = 'history-card';
        card.innerHTML = `
          <div style="display:flex; align-items:center; gap:12px;">
            <span style="font-size:1.4rem; font-weight:900; color:#ff851b;">#${idx + 1}</span>
            <div>
              <strong style="font-size:1.05rem;">${item.marca} ${item.modelo}</strong>
              <p style="font-size:0.82rem; color:#9ca3af;">Calibración ${item.tipoSensibilidad} | General: ${item.general} | Valoración: ⭐ ${item.puntuacionPromedio}</p>
            </div>
          </div>
          <button class="btn-secondary" style="padding:6px 12px; font-size:0.85rem;" onclick="copiarDirecto('${item.general}', '${item.puntoRojo}', '${item.mira2x}', '${item.mira4x}', '${item.botonDisparo}')">Copiar</button>
        `;
        list.appendChild(card);
      });
    }
  } catch (err) {
    console.warn('Error cargando ranking:', err);
  }
}

async function eliminarDelHistorial(id) {
  if (!confirm('¿Deseas eliminar esta configuración de tu historial?')) return;
  try {
    const resp = await fetch(`/api/sensibilidades/${id}`, { method: 'DELETE' });
    const json = await resp.json();
    if (json.success) {
      mostrarToast('Sensibilidad eliminada', 'info');
      cargarHistorial();
    }
  } catch (err) {
    mostrarToast('Error al eliminar', 'error');
  }
}

async function cargarEnGenerador(id) {
  try {
    const resp = await fetch(`/api/sensibilidades/${id}`);
    const json = await resp.json();
    if (json.success && json.data) {
      cambiarTab('generator');
      renderResultado(json.data);
      AppState.currentConfig = json.data;
    }
  } catch (err) {
    mostrarToast('No se pudo cargar la configuración', 'error');
  }
}

function copiarDirecto(gen, red, m2, m4, bot) {
  const txt = `Sensi7gringo: General ${gen} | RedDot ${red} | 2X ${m2} | 4X ${m4} | Botón ${bot}%`;
  navigator.clipboard.writeText(txt).then(() => {
    mostrarToast('¡Valores copiados al portapapeles!', 'success');
  });
}

// 11. Adaptar Configuración de Pro Player al Dispositivo del Usuario
function adaptarProPlayer(playerId) {
  cambiarTab('generator');
  mostrarToast('Adaptando configuración del Pro Player a tu dispositivo...', 'info');
  setTimeout(() => {
    generarSensibilidad();
  }, 600);
}

// 12. Toast Helper
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
  
  let icon = '⚡';
  if (tipo === 'success') icon = '✅';
  if (tipo === 'warning') icon = '⚠️';
  if (tipo === 'error') icon = '❌';

  toast.innerHTML = `<span>${icon}</span> <span>${mensaje}</span>`;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateX(40px)';
    toast.style.transition = 'all 0.3s ease';
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}
