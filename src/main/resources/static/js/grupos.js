// ------------------------------
// Variables globales
// ------------------------------


function mostrarValor(valor) {
  return (valor === 0) ? "NA" : (valor || "");
}

const filasEstudiantes = {};  // { matricula: { matricula, nombreCompleto, calificaciones: { idMateria: { corte1, corte2, corte3, calificacionOrdinaria, recuperacion1, recuperacion2, recuperacion3, calificacionOrdinariaR } } } }
let materiasCargadas = [];
let materiaNombres = {};      // { idMateria: nombre }

// ----- Variables para paginación de alumnos -----
let listaAlumnos = [];        // Acá guardaremos, al final de cargar, el arreglo de filas
let currentPage = 1;          // Página actual (inicia en 1)
const pageSize = 10;          // Cantidad de alumnos por página
let totalPages = 0;           // Se calcula cuando terminemos de cargar todos los alumnos
let graficaCalificaciones = null;

// ------------------------------
// 1) Cargar grupos por semestre
// ------------------------------
async function cargarGruposPorSemestre() {
  const idSemestre = document.getElementById("idSemestre").value;
  const grupoSelect = document.getElementById("idGrupo");
  grupoSelect.innerHTML = '<option value="">Selecciona un grupo</option>';
  if (!idSemestre) return;

  try {
    const response = await fetch(`/api/gruposPorSemestre?idSemestre=${idSemestre}`);
    if (!response.ok) throw new Error(response.statusText);
    const grupos = await response.json();
    grupos.forEach(grupo => {
      grupoSelect.innerHTML += `<option value="${grupo.idGrupo}">${grupo.nombre}</option>`;
    });
  } catch (error) {
    console.error("Error al cargar grupos:", error);
  }
}

// ----------------------------------------------------
// 2) Cargar materias por grupo y generar pestañas
// ----------------------------------------------------
async function cargarMateriasPorGrupoTabs() {
  const idGrupo = document.getElementById("idGrupo").value;
  const tabContainer = document.getElementById("tabContainer");
  const tabContentContainer = document.getElementById("tabContentContainer");
  tabContainer.innerHTML = "";
  tabContentContainer.innerHTML = "";
  if (!idGrupo) return;

  try {
    const response = await fetch(`/api/materias/por-grupo/${idGrupo}`);
    if (!response.ok) throw new Error(response.statusText);
    const materias = await response.json();

    // Reset variables
    materiasCargadas = [];
    materiaNombres = {};

    // --- 2.1) Pestañas individuales por materia ---
    materias.forEach((materia, index) => {
      materiasCargadas.push(materia.idMateria);
      materiaNombres[materia.idMateria] = materia.materiaDefecto.nombre;

      // Crear elemento de pestaña
      const tab = document.createElement("div");
      tab.className = "tab" + (index === 0 ? " active" : "");
      tab.textContent = materia.materiaDefecto.nombre;
      tab.onclick = () => cambiarPestana(index);
      tabContainer.appendChild(tab);

      // Crear contenedor de contenido de pestaña
      const tabContent = document.createElement("div");
      tabContent.className = "tab-content" + (index === 0 ? " active" : "");
      tabContent.id = `tabContent${materia.idMateria}`;
      tabContent.innerHTML = `
        <button class="btn btn-primary mb-3" onclick="generarReportePDFPorPestana('tabContent${materia.idMateria}', '${materia.materiaDefecto.nombre}')">
          Generar PDF de esta pestaña
        </button>
        
        <table class="table table-bordered table-striped">
          <thead class="table-dark">
            <tr>
              <th>No</th>
              <th>Matrícula</th>
              <th>Estudiante</th>
              <th style="color:red;">I</th>
              <th style="color:red;">II</th>
              <th style="color:red;">III</th>
              <th style="font-weight:bold;">CAL</th>
              <th>R1</th>
              <th>R2</th>
              <th>R3</th>
              <th style="font-weight:bold;">CAL</th>
              <th>Desempeño</th>
              <th>Comentario</th>
            </tr>
          </thead>
          <tbody id="cuerpoTabla${materia.idMateria}"></tbody>
        </table>
        <div class="mt-4">
          <canvas id="chartMateria${materia.idMateria}" style="max-width:600px;" width="600" height="300"></canvas>
        </div>
        <!-- Nueva sección para gráficas avanzadas -->
        <div id="plotlyCalifDiv${materia.idMateria}" style="width:100%;height:400px;"></div>
         <div class="mb-2">
          <button class="btn btn-outline-secondary btn-sm" onclick="crearGraficaCalificacionesPorMateriaAvanzada(${materia.idMateria}, 'plotlyCalifDiv${materia.idMateria}', 'completo')">Vista Completa</button>
          <button class="btn btn-outline-secondary btn-sm" onclick="crearGraficaCalificacionesPorMateriaAvanzada(${materia.idMateria}, 'plotlyCalifDiv${materia.idMateria}', 'interactiva')">Vista Interactiva</button>
        </div>
        <div id="plotlyCorte1_${materia.idMateria}" style="width:100%;height:300px;"></div>
        <div id="plotlyCorte2_${materia.idMateria}" style="width:100%;height:300px;"></div>
        <div id="plotlyCorte3_${materia.idMateria}" style="width:100%;height:300px;"></div>
      `;
      tabContentContainer.appendChild(tabContent);
    });

    // --- 2.2) Pestaña "Todas las Materias" ---
    const tabAllIndex = materias.length;
    const tabAll = document.createElement("div");
    tabAll.className = "tab";
    tabAll.textContent = "Todas las Materias";
    tabAll.onclick = () => cambiarPestana(tabAllIndex);
    tabContainer.appendChild(tabAll);

    const tabContentAll = document.createElement("div");
    tabContentAll.className = "tab-content";
    tabContentAll.id = "tabContentTodasMaterias";
    tabContentAll.innerHTML = `
      <button class="btn btn-primary mb-3" onclick="generarReportePDFPorPestana('tabContentTodasMaterias', 'Todas las Materias')">
        Generar PDF de Todas las Materias
      </button>
      <div id="scrollbarContainerTop" style="overflow-x: auto; height: 20px; border: 1px solid #dee2e6; margin-bottom: 5px;">
        <div id="dummyScrollTop" style="height: 1px; width: 2000px;"></div>
      </div>
      <div class="table-responsive" id="tableContainer">
        <table class="table table-bordered table-hover table-striped">
          <thead id="encabezadosConsolidado"></thead>
          <tbody id="cuerpoTablaConsolidado"></tbody>
        </table>
      </div>
      <div id="scrollbarContainerBottom" style="overflow-x: auto; height: 20px; border: 1px solid #dee2e6; margin-top: 5px;">
        <div id="dummyScrollBottom" style="height: 1px; width: 2000px;"></div>
      </div>
      <nav aria-label="Paginación Alumnos" class="mt-3">
        <ul class="pagination justify-content-center" id="paginationControls"></ul>
      </nav>
    `;
    tabContentContainer.appendChild(tabContentAll);

    // --- 2.3) Pestaña "Índice de Reprobación/Aprobación" ---
    const tabIndiceIndex = materias.length + 1;
    const tabIndice = document.createElement("div");
    tabIndice.className = "tab";
    tabIndice.textContent = "Índice de Reprobación/Aprobación";
    tabIndice.onclick = () => cambiarPestana(tabIndiceIndex);
    tabContainer.appendChild(tabIndice);

    const tabContentIndice = document.createElement("div");
    tabContentIndice.className = "tab-content";
    tabContentIndice.id = "tabContentIndices";
    tabContentIndice.innerHTML = `
      <button class="btn btn-primary mb-3" onclick="generarReportePDFPorPestana('tabContentIndices', 'Índice de Reprobación/Aprobación')">
        Generar PDF de Índice de Reprobación/Aprobación
      </button>
      <h3>Estadísticas de Reprobación y Aprobación</h3>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>Materia</th>
            <th>Total Alumnos</th>
            <th>Reprobados</th>
            <th>% Reprobación</th>
            <th>Aprobados</th>
            <th>% Aprobación</th>
          </tr>
        </thead>
        <tbody id="tablaReprobacion"></tbody>
      </table>
      <canvas id="graficaIndices" style="max-width:600px;" width="600" height="300"></canvas>
      <hr>
      <h4>Distribución de Calificaciones por Materia</h4>
      <div class="mb-3">
        <label for="materiaSelectGrafica">Selecciona una materia:</label>
        <select id="materiaSelectGrafica" class="form-select">
          <option value="">Selecciona una materia</option>
        </select>
      </div>
      <canvas id="graficaCalificacionesPorMateria" style="max-width:600px;" width="600" height="300"></canvas>
    `;
    tabContentContainer.appendChild(tabContentIndice);

    // --- 2.4) Llenar select de materia para gráfica ---
    const materiaSelect = document.getElementById("materiaSelectGrafica");
    if (materiaSelect) {
      materiaSelect.innerHTML = '<option value="">Selecciona una materia</option>';
      materias.forEach(materia => {
        materiaSelect.innerHTML += `<option value="${materia.idMateria}">${materia.materiaDefecto.nombre}</option>`;
      });
       // Aquí se agrega el listener:
       materiaSelect.addEventListener("change", handleMateriaGraficaChange);
    }

    // --- 2.5) Cargar calificaciones individuales para cada pestaña ---
    for (const materia of materias) {
      await cargarCalificacionesPorMateria(idGrupo, materia.idMateria);
    }

    

    // Al final, cargar y paginar "Todas las Materias"
    await cargarTodasLasMateriasPorGrupo();

  } catch (error) {
    console.error("Error al cargar materias (Tabs):", error);
  }
}

// ----------------------------------------------------
// 3) Cargar calificaciones de una materia (pestaña individual)
// ----------------------------------------------------
async function cargarCalificacionesPorMateria(idGrupo, idMateria) {
  if (!idGrupo || !idMateria) return;

  const loadingElem = document.getElementById("loading");
  if (loadingElem) loadingElem.style.display = "block";

  const cuerpoTabla = document.getElementById(`cuerpoTabla${idMateria}`);
  try {
    const response = await fetch(`/por-materia/${idMateria}`);
    if (!response.ok) throw new Error(`Error en la respuesta: ${response.statusText}`);
    const calificaciones = await response.json();

    let contadorFila = 1;
    if (cuerpoTabla) cuerpoTabla.innerHTML = "";

    calificaciones.forEach(calif => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${contadorFila}</td>
        <td>${calif.matricula}</td>
        <td>${calif.nombreAlumno} ${calif.apellidoPaterno} ${calif.apellidoMaterno}</td>
        <td>${mostrarValor(calif.corte1)}</td>
        <td>${mostrarValor(calif.corte2)}</td>
        <td>${mostrarValor(calif.corte3)}</td>
        <td>${calif.calificacionOrdinaria}</td>
        <td>${mostrarValor(calif.recuperacion1)}</td>
        <td>${mostrarValor(calif.recuperacion2)}</td>
        <td>${mostrarValor(calif.recuperacion3)}</td>
        <td>${calif.calificacionOrdinariaR}</td>
        <td>${calif.desempeno}</td>
        <td>${calif.comentario}</td>
      `;
      cuerpoTabla.appendChild(fila);
      contadorFila++;
    });

    // Generar gráfica de barras para “picos” (Ordinaria vs Recuperación)
    //crearGraficaPicosPorMateria(idMateria, calificaciones);

  } catch (error) {
    console.error("Error al cargar calificaciones (Tabs):", error);
    if (cuerpoTabla) {
      cuerpoTabla.innerHTML = "<tr><td colspan='13'>Error al obtener calificaciones.</td></tr>";
    }
  } finally {
    if (loadingElem) loadingElem.style.display = "none";
  }
}

// ----------------------------------------------------
// 4) Cargar tabla consolidada “Todas las Materias”
// ----------------------------------------------------
async function cargarTodasLasMateriasPorGrupo() {
  const idGrupo = document.getElementById("idGrupo").value;
  const tabContentAll = document.getElementById("tabContentTodasMaterias");
  const cabezaConsolidados = document.getElementById("encabezadosConsolidado");
  const cuerpoConsolidado = document.getElementById("cuerpoTablaConsolidado");
  if (cuerpoConsolidado) cuerpoConsolidado.innerHTML = "";
  if (!idGrupo || !tabContentAll) return;

  // Limpiar filasEstudiantes
  for (const key in filasEstudiantes) {
    if (filasEstudiantes.hasOwnProperty(key)) {
      delete filasEstudiantes[key];
    }
  }

  try {
    const materiasResponse = await fetch(`/api/materias/por-grupo/${idGrupo}`);
    if (!materiasResponse.ok) throw new Error(materiasResponse.statusText);
    const materias = await materiasResponse.json();

    materiasCargadas = [];
    materias.forEach(materia => materiasCargadas.push(materia.idMateria));

    // --- 4.1) Construir encabezados de la tabla consolidada dinámicamente ---
    let encabezados = `<tr class="table-dark">
                         <th rowspan='3'>No</th>
                         <th rowspan='3'>Matrícula</th>
                         <th rowspan='3'>Estudiante</th>`;
    materias.forEach(materia => {
      encabezados += `<th colspan='8' class='materia' onclick="toggleCalificaciones('${materia.idMateria}')">
                         ${materia.materiaDefecto.nombre}
                       </th>`;
    });
    encabezados += `<th rowspan='3'>PROM GRAL</th>
                   <th rowspan='3'>NIVEL DE DESEMPEÑO</th>
                 </tr>`;
    encabezados += `<tr class="table-secondary">`;
    materias.forEach(() => {
      encabezados += `<th colspan='4'>ORDINARIAS</th>
                     <th colspan='4'>RECUPERACIONES</th>`;
    });
    encabezados += `</tr>`;
    encabezados += `<tr class="table-secondary">`;
    materias.forEach(() => {
      encabezados += `<th style="color:red;">I</th>
                     <th style="color:red;">II</th>
                     <th style="color:red;">III</th>
                     <th style="font-weight:bold;">CAL</th>
                     <th>R1</th>
                     <th>R2</th>
                     <th>R3</th>
                     <th style="font-weight:bold;">CAL</th>`;
    });
    encabezados += `</tr>`;
    if (cabezaConsolidados) cabezaConsolidados.innerHTML = encabezados;

    // --- 4.2) Configurar scroll sincronizado ---
    const scrollbarContainerTop = document.getElementById("scrollbarContainerTop");
    const scrollbarContainerBottom = document.getElementById("scrollbarContainerBottom");
    const tableContainer = document.getElementById("tableContainer");
    const dummyScrollTop = document.getElementById("dummyScrollTop");
    const dummyScrollBottom = document.getElementById("dummyScrollBottom");

    if (scrollbarContainerTop && scrollbarContainerBottom && tableContainer && dummyScrollTop && dummyScrollBottom) {
      const adjustDummyScrollWidth = () => {
        const tableWidth = tableContainer.scrollWidth;
        dummyScrollTop.style.width = tableWidth + "px";
        dummyScrollBottom.style.width = tableWidth + "px";
      };
      adjustDummyScrollWidth();
      const observer = new MutationObserver(adjustDummyScrollWidth);
      observer.observe(tableContainer, { childList: true, subtree: true });

      scrollbarContainerTop.addEventListener("scroll", () => {
        tableContainer.scrollLeft = scrollbarContainerTop.scrollLeft;
        scrollbarContainerBottom.scrollLeft = scrollbarContainerTop.scrollLeft;
      });
      scrollbarContainerBottom.addEventListener("scroll", () => {
        tableContainer.scrollLeft = scrollbarContainerBottom.scrollLeft;
        scrollbarContainerTop.scrollLeft = scrollbarContainerBottom.scrollLeft;
      });
      tableContainer.addEventListener("scroll", () => {
        scrollbarContainerTop.scrollLeft = tableContainer.scrollLeft;
        scrollbarContainerBottom.scrollLeft = tableContainer.scrollLeft;
      });
    }

    // --- 4.3) Cargar calificaciones de cada materia para llenar filasEstudiantes ---
    for (const materia of materias) {
      await cargarCalificacionesPorGrupo(idGrupo, materia.idMateria, materias.length);
    }

    // --- 4.4) Pasar filasEstudiantes a listaAlumnos y calcular paginación ---
    listaAlumnos = Object.values(filasEstudiantes);
    totalPages = Math.ceil(listaAlumnos.length / pageSize);

    // --- 4.5) Mostrar la primera página ---
    renderTablePage(1);

  } catch (error) {
    console.error("Error al cargar la tabla consolidada:", error);
  }
}

// ----------------------------------------------------
// 4.1) Cargar calificaciones y llenar filasEstudiantes
// ----------------------------------------------------
async function cargarCalificacionesPorGrupo(idGrupo, idMateria, totalMaterias) {
  if (!idGrupo || !idMateria) return;

  const loadingElem = document.getElementById("loading");
  if (loadingElem) loadingElem.style.display = "block";

  try {
    const response = await fetch(`/por-materia/${idMateria}`);
    if (!response.ok) throw new Error(`Error en la respuesta: ${response.statusText}`);
    const calificaciones = await response.json();

    // Recorrer cada calificación y poblar filasEstudiantes
    calificaciones.forEach(calif => {
      const matricula = calif.matricula;
      let filaObj = filasEstudiantes[matricula];

      // Si no existe aún, creamos el objeto con propiedades básicas
      if (!filaObj) {
        filaObj = {
          matricula: matricula,
          nombreCompleto: `${calif.nombreAlumno} ${calif.apellidoPaterno} ${calif.apellidoMaterno}`,
          calificaciones: {}
        };
        filasEstudiantes[matricula] = filaObj;
      }

      // Llenar las calificaciones para esta materia
      filaObj.calificaciones[idMateria] = {
        corte1: calif.corte1,
        corte2: calif.corte2,
        corte3: calif.corte3,
        calificacionOrdinaria: calif.calificacionOrdinaria,
        recuperacion1: calif.recuperacion1,
        recuperacion2: calif.recuperacion2,
        recuperacion3: calif.recuperacion3,
        calificacionOrdinariaR: calif.calificacionOrdinariaR
      };
    });

    // Después de llenar todas las materias, calculamos promedios y niveles
    for (const matricula in filasEstudiantes) {
      if (filasEstudiantes.hasOwnProperty(matricula)) {
        const filaObj = filasEstudiantes[matricula];
        const promedioGeneral = calcularPromedioGeneral(matricula, totalMaterias);
        const nivelDesempeño = determinarNivelDesempeño(promedioGeneral);
        filaObj.promedioGeneral = promedioGeneral;
        filaObj.nivelDesempeño = nivelDesempeño;
      }
    }

  } catch (error) {
    console.error("Error al cargar calificaciones consolidadas:", error);
  } finally {
    if (loadingElem) loadingElem.style.display = "none";
  }
}

// ----------------------------------------------------
// 5) Mostrar/ocultar calificaciones de una materia
// ----------------------------------------------------
function toggleCalificaciones(idMateria) {
  const filas = document.querySelectorAll("#cuerpoTablaConsolidado tr");
  const materiaIndex = materiasCargadas.indexOf(idMateria);
  const baseIndex = 3 + materiaIndex * 8;
  filas.forEach(fila => {
    const celdas = fila.children;
    for (let i = baseIndex; i < baseIndex + 8; i++) {
      celdas[i].classList.toggle("hidden");
    }
  });
}

// ----------------------------------------------------
// 6) Cálculo de promedio general por alumno
// ----------------------------------------------------
function calcularPromedioGeneral(matricula, totalMaterias) {
  const filaObj = filasEstudiantes[matricula];
  if (!filaObj) return "NA";

  let sumaOrdinarias = 0, contadorOrdinarias = 0;
  let sumaRecuperaciones = 0, contadorRecuperaciones = 0;

  materiasCargadas.forEach(idMateria => {
    const datos = filaObj.calificaciones[idMateria] || {};
    const ord = parseFloat(datos.calificacionOrdinaria) || 0;
    const rec = parseFloat(datos.calificacionOrdinariaR) || 0;
    if (ord > 0) {
      sumaOrdinarias += ord;
      contadorOrdinarias++;
    }
    if (rec > 0) {
      sumaRecuperaciones += rec;
      contadorRecuperaciones++;
    }
  });

  const totalSuma = sumaOrdinarias + sumaRecuperaciones;
  const totalContador = contadorOrdinarias + contadorRecuperaciones;

  if (totalContador > 0) {
    return (totalSuma / totalContador).toFixed(2);
  } else {
    return "NA";
  }
}

function determinarNivelDesempeño(promedio) {
  if (promedio === "NA") return "NA";
  const val = parseFloat(promedio);
  if (val >= 90) return "Excelente";
  if (val >= 80) return "Bueno";
  if (val >= 70) return "Regular";
  return "Deficiente";
}

// ----------------------------------------------------
// 7) Manejo de cambio en select para distribución gráfica
// ----------------------------------------------------

async function handleMateriaGraficaChange() {
  const materiaSelect = document.getElementById("materiaSelectGrafica");
  const materiaId = materiaSelect.value;
  if (!materiaId) return;
  await crearGraficaCalificacionesPorMateria(materiaId);
}




// ----------------------------------------------------
// 8) Crear gráfica de distribución de calificaciones por materia
// ----------------------------------------------------

let chartCalificacionesPorMateria = null;

async function crearGraficaCalificacionesPorMateria(materiaId) {
  try {
    const response = await fetch(`/por-materia/${materiaId}`);
    if (!response.ok) throw new Error("Error al obtener datos");
    const calificaciones = await response.json();

    const alumnos = calificaciones.map(c => `${c.nombreAlumno} ${c.apellidoPaterno}`);
    const notasOrdinarias = calificaciones.map(c => +c.calificacionOrdinaria || 0);
    const notasRecuperacion = calificaciones.map(c => +c.calificacionOrdinariaR || 0);

    const oldCanvas = document.getElementById("graficaCalificacionesPorMateria");
    if (!oldCanvas) return;

    // Reemplazar solo el canvas, no el contenedor completo
    const newCanvas = document.createElement("canvas");
    newCanvas.id = "graficaCalificacionesPorMateria";
    newCanvas.style.maxWidth = "600px";
    newCanvas.width = 600;
    newCanvas.height = 300;

    oldCanvas.parentNode.replaceChild(newCanvas, oldCanvas);
    const ctx = newCanvas.getContext("2d");

    // Destruir gráfico anterior si existe
    if (window.chartCalificacionesPorMateria) {
      window.chartCalificacionesPorMateria.destroy();
    }

    // Crear nuevo gráfico
    window.chartCalificacionesPorMateria = new Chart(ctx, {
      type: "bar",
      data: {
        labels: alumnos,
        datasets: [
          {
            label: "Ordinaria",
            data: notasOrdinarias,
            backgroundColor: "rgba(33, 150, 243, 0.7)",
            borderColor: "rgba(33, 150, 243, 1)",
            borderWidth: 1
          },
          {
            label: "Recuperación",
            data: notasRecuperacion,
            backgroundColor: "rgba(229, 57, 53, 0.7)",
            borderColor: "rgba(229, 57, 53, 1)",
            borderWidth: 1
          }
        ]
      },
      options: {
        plugins: {
          legend: { position: 'bottom' },
          title: {
            display: true,
            text: materiaNombres[materiaId] || 'Materia',
            font: { size: 20 }
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            max: 110
          }
        }
      }
    });
  } catch (error) {
    console.error(`Error al cargar calificaciones para la materia ${materiaId}:`, error);
  }
}




// ----------------------------------------------------
// 9) Crear gráfica de picos (Ordinaria vs Recuperación) por materia
// ----------------------------------------------------
function crearGraficaPicosPorMateria(idMateria, calificaciones) {
  const canvas = document.getElementById(`chartMateria${idMateria}`);
  const devicePixelRatio = window.devicePixelRatio || 1;
  const canvasWidth = 600, canvasHeight = 300;

  canvas.width = canvasWidth * devicePixelRatio;
  canvas.height = canvasHeight * devicePixelRatio;
  canvas.style.width = `${canvasWidth}px`;
  canvas.style.height = `${canvasHeight}px`;
  const ctx = canvas.getContext("2d");
  ctx.scale(devicePixelRatio, devicePixelRatio);

  const labels = calificaciones.map(c => `${c.nombreAlumno} ${c.apellidoPaterno}`);
  const notasOrdinarias = calificaciones.map(c => {
    let val = parseFloat(c.calificacionOrdinaria);
    return isNaN(val) ? null : val;
  });
  const notasRecuperacion = calificaciones.map(c => {
    let val = parseFloat(c.calificacionOrdinariaR);
    return isNaN(val) ? null : val;
  });

  new Chart(ctx, {
    type: "bar",
    data: {
      labels: labels,
      datasets: [
        {
          label: "Calificación Ordinaria",
          data: notasOrdinarias,
          backgroundColor: "rgba(153, 102, 255, 0.5)",
          borderColor: "rgba(153, 102, 255, 1)",
          borderWidth: 1
        },
        {
          label: "Calificación Recuperación",
          data: notasRecuperacion,
          backgroundColor: "rgba(255, 159, 64, 0.5)",
          borderColor: "rgba(255, 159, 64, 1)",
          borderWidth: 1
        }
      ]
    },
    options: {
      indexAxis: "x",
      scales: {
        y: { beginAtZero: true, max: 100 }
      },
      plugins: {
        tooltip: {
          callbacks: {
            label: function (context) {
              return ` ${context.parsed.y}`;
            }
          }
        }
      }
    }
  });
}


// ----------------------------------------------------
// 10) Cálculo de índices de reprobación/aprobación
// ----------------------------------------------------
async function cargarDatosReprobacion() {
  const tablaReprobacion = document.getElementById("tablaReprobacion");
  if (tablaReprobacion) tablaReprobacion.innerHTML = "";
  const dataReprobacion = [];
  const dataAprobacion = [];
  const threshold = 60;

  for (const materiaId of materiasCargadas) {
    try {
      const response = await fetch(`/por-materia/${materiaId}`);
      if (!response.ok) throw new Error("Error al obtener datos");
      const calificaciones = await response.json();
      const total = calificaciones.length;

      const reprobados = calificaciones.filter(calif => {
        const califFinal = calif.calificacionOrdinariaR || calif.calificacionOrdinaria;
        return parseFloat(califFinal) < threshold;
      }).length;
      const aprobados = total - reprobados;
      const porcentajeReprobacion = total > 0 ? ((reprobados / total) * 100).toFixed(2) : "0";
      const porcentajeAprobacion = total > 0 ? ((aprobados / total) * 100).toFixed(2) : "0";

      if (tablaReprobacion) {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${materiaNombres[materiaId] || materiaId}</td>
          <td>${total}</td>
          <td>${reprobados}</td>
          <td>${porcentajeReprobacion}%</td>
          <td>${aprobados}</td>
          <td>${porcentajeAprobacion}%</td>
        `;
        tablaReprobacion.appendChild(row);
      }

      dataReprobacion.push({ materia: materiaNombres[materiaId] || materiaId, porcentaje: parseFloat(porcentajeReprobacion) });
      dataAprobacion.push({ materia: materiaNombres[materiaId] || materiaId, porcentaje: parseFloat(porcentajeAprobacion) });
    } catch (error) {
      console.error(`Error al obtener datos para la materia ${materiaId}:`, error);
    }
  }

  crearGraficaIndices(dataReprobacion, dataAprobacion);
}

// ----------------------------------------------------
// 11) Crear gráfica de Índices (reprobación / aprobación)
// ----------------------------------------------------
function crearGraficaIndices(dataReprobacion, dataAprobacion) {
  const canvas = document.getElementById("graficaIndices");
  const devicePixelRatio = window.devicePixelRatio || 1;
  const canvasWidth = 600, canvasHeight = 300;

  canvas.width = canvasWidth * devicePixelRatio;
  canvas.height = canvasHeight * devicePixelRatio;
  canvas.style.width = `${canvasWidth}px`;
  canvas.style.height = `${canvasHeight}px`;
  const ctx = canvas.getContext("2d");
  ctx.scale(devicePixelRatio, devicePixelRatio);

  const labels = dataReprobacion.map(item => item.materia);
  const datosReprob = dataReprobacion.map(item => item.porcentaje);
  const datosAprob = dataAprobacion.map(item => item.porcentaje);

  new Chart(ctx, {
    type: "bar",
    data: {
      labels: labels,
      datasets: [
        {
          label: "% Reprobación",
          data: datosReprob,
          backgroundColor: "rgba(255, 99, 132, 0.5)",
          borderColor: "rgba(255, 99, 132, 1)",
          borderWidth: 1
        },
        {
          label: "% Aprobación",
          data: datosAprob,
          backgroundColor: "rgba(75, 192, 192, 0.5)",
          borderColor: "rgba(75, 192, 192, 1)",
          borderWidth: 1
        }
      ]
    },
    options: {
      scales: {
        y: { beginAtZero: true, max: 100 }
      }
    }
  });
}

// ----------------------------------------------------
// 12) Cambiar pestaña (Tab switching)
// ----------------------------------------------------
function cambiarPestana(index) {
  const tabs = document.querySelectorAll(".tab");
  const tabContents = document.querySelectorAll(".tab-content");
  tabs.forEach((tab, i) => tab.classList.toggle("active", i === index));
  tabContents.forEach((content, i) => content.classList.toggle("active", i === index));

  // Si la pestaña es "Índice de Reprobación/Aprobación"
  if (index === materiasCargadas.length + 1) {
    cargarDatosReprobacion();
  }

  // 🔹 Si la pestaña es "Todas las Materias" (índice = materiasCargadas.length)
  if (index === materiasCargadas.length) {
    // Dejamos un breve delay para que el DOM haya aplicado la clase "active" y ya no esté oculta
    setTimeout(() => {
      ajustarScrollDummy();
    }, 50);
  }
  // Si es una pestaña de materia individual, inicializa las gráficas avanzadas
  if (index >= 0 && index < materiasCargadas.length) {
    const materiaId = materiasCargadas[index];
    initGraficasRendimiento(materiaId);
  }
}

// ----------------------------------------------------
// 13) Función general al cambiar selección de grupo
// ----------------------------------------------------
async function handleSelectChange() {
  await cargarMateriasPorGrupoTabs();
  // La carga paginada de "Todas las Materias" ya se invoca dentro de cargarMateriasPorGrupoTabs()
}

// ----------------------------------------------------
// 14) Generar PDF de una sección (utiliza html2pdf.js)
// ----------------------------------------------------
function generarReportePDFPorPestana(idElemento, nombrePestana) {
  const element = document.getElementById(idElemento);
  const dummyScrollTop = document.getElementById("dummyScrollTop");
  if (!element) return;

  const esTablaConsolidada = idElemento === "tabContentTodasMaterias";
  let paginacionOriginal = null;
  let cuerpoConsolidado = null;

  if (esTablaConsolidada) {
    cuerpoConsolidado = document.getElementById("cuerpoTablaConsolidado");
    if (cuerpoConsolidado) {
      // 1) Guardar la paginación original y vaciar el cuerpo
      paginacionOriginal = cuerpoConsolidado.innerHTML;
      cuerpoConsolidado.innerHTML = "";

      // 2) Reconstruir filas completas (sin paginación)
      listaAlumnos.forEach((alumno, idx) => {
        let filaHTML = `<tr>
          <td>${idx + 1}</td>
          <td>${alumno.matricula}</td>
          <td>${alumno.nombreCompleto}</td>`;
        materiasCargadas.forEach((idMat) => {
          const d = alumno.calificaciones[idMat] || {};
          filaHTML += `
            <td style="color:red;">${mostrarValor(d.corte1)}</td>
            <td style="color:red;">${mostrarValor(d.corte2)}</td>
            <td style="color:red;">${mostrarValor(d.corte3)}</td>
            <td style="font-weight:bold;">${d.calificacionOrdinaria || ""}</td>
            <td>${mostrarValor(d.recuperacion1)}</td>
            <td>${mostrarValor(d.recuperacion2)}</td>
            <td>${mostrarValor(d.recuperacion3)}</td>
            <td style="font-weight:bold;">${d.calificacionOrdinariaR || ""}</td>`;
        });
        filaHTML += `
          <td>${alumno.promedioGeneral}</td>
          <td>${alumno.nivelDesempeño}</td>
        </tr>`;
        cuerpoConsolidado.insertAdjacentHTML("beforeend", filaHTML);
      });

      // 3) Reconstruir encabezado THEAD con dos filas simples
            const cabeza = document.getElementById("encabezadosConsolidado");
      if (cabeza) {
        cabeza.innerHTML = `
          <tr style="background:#343a40;">
            <th rowspan="2" style="background:#343a40;color:#fff;">No</th>
            <th rowspan="2" style="background:#343a40;color:#fff;">Matrícula</th>
            <th rowspan="2" style="background:#343a40;color:#fff;">Estudiante</th>
            ${materiasCargadas.map(id => `<th colspan="8" style="background:#343a40;color:#fff;">${materiaNombres[id]}</th>`).join('')}
            <th rowspan="2" style="background:#343a40;color:#fff;">PROM GRAL</th>
            <th rowspan="2" style="background:#343a40;color:#fff;">NIVEL DE DESEMPEÑO</th>
          </tr>
          <tr style="background:#6c757d;">
            ${materiasCargadas.map(_ =>
              `<th style="background:#6c757d;color:#fff;">I</th>
              <th style="background:#6c757d;color:#fff;">II</th>
              <th style="background:#6c757d;color:#fff;">III</th>
              <th style="background:#6c757d;color:#fff;">CAL</th>
              <th style="background:#6c757d;color:#fff;">R1</th>
              <th style="background:#6c757d;color:#fff;">R2</th>
              <th style="background:#6c757d;color:#fff;">R3</th>
              <th style="background:#6c757d;color:#fff;">CAL</th>`
            ).join('')}
          </tr>
        `;
        
      }
      element.querySelectorAll("thead").forEach(t => t.style.display = "table-header-group");
      
    }
    
  }

    
  // 4) Guardar estilos originales
  const originalOverflow = element.style.overflow;
  const originalWidth = element.style.width;
  const originalDummyDisplay = dummyScrollTop ? dummyScrollTop.style.display : null;

  // 5) Ajustar para PDF
  element.style.overflow = "visible";
  element.style.width = "auto";

  // 6) Insertar título al principio
  const titulo = document.createElement("h1");
  titulo.textContent = nombrePestana;
  titulo.style.textAlign = "center";
  titulo.style.marginBottom = "20px";
  element.insertBefore(titulo, element.firstChild);

  // 7) Asegurar quiebre de página tras cada tabla o imagen
  element.querySelectorAll("table, img").forEach(el => {
    el.style.pageBreakAfter = "always";
  });

  // 8) Reemplazar gráficas Plotly por imágenes PNG
  const plotlyImgs = [];
  element.querySelectorAll("div[id^='plotly']").forEach((div, idx) => {
    Plotly.toImage(div, {
      format: 'png',
      width: div.offsetWidth,
      height: div.offsetHeight
    }).then(dataUrl => {
      const img = document.createElement("img");
      img.src = dataUrl;
      img.style.width = "100%";
      img.style.height = "auto";
      img.style.marginBottom = "200px";
      if (idx < document.querySelectorAll("div[id^='plotly']").length - 1) {
        img.style.pageBreakAfter = "always";
      }
      div.parentNode.replaceChild(img, div);
      plotlyImgs.push({ img, div });
    });
  });

  // 9) Reemplazar canvases (Chart.js) por imágenes PNG
  const canvasImgs = [];
  element.querySelectorAll("canvas").forEach((canvas, idx, list) => {
    const img = document.createElement("img");
    img.src = canvas.toDataURL("image/png", 1.0);
    img.style.width = "60%";
    img.style.height = "auto";
    img.style.marginBottom = "200px";
    if (idx < list.length - 1) {
      img.style.pageBreakAfter = "always";
    }
    canvas.parentNode.replaceChild(img, canvas);
    canvasImgs.push({ img, canvas });
  });

  // 10) Ocultar temporalmente los botones de control
  const hiddenBtns = [];
  element.querySelectorAll("button").forEach(btn => {
    const txt = btn.textContent || "";
    if (/Generar PDF|Vista Completa|Interactiva/.test(txt)) {
      hiddenBtns.push(btn);
      btn.style.display = "none";
    }
  });

  if (dummyScrollTop) dummyScrollTop.style.display = "none";

  // 11) Generar PDF tras un breve delay para procesar imágenes Plotly
  setTimeout(() => {
    const formato = esTablaConsolidada ? [70, 33.1] : "a4";
    html2pdf().from(element).set({
      margin: 0.5,
      filename: `${nombrePestana}.pdf`,
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: { scale: 2, scrollY: 0 },
      jsPDF: {
        unit: "in",
        format: formato,
        orientation: "landscape"
      }
    }).save().then(() => {
      // 12) Restaurar estilos originales
      element.style.overflow = originalOverflow;
      element.style.width = originalWidth;
      if (dummyScrollTop) dummyScrollTop.style.display = originalDummyDisplay;
      hiddenBtns.forEach(b => b.style.display = "inline-block");

      // 13) Restaurar paginación y encabezado original
      if (esTablaConsolidada && cuerpoConsolidado && paginacionOriginal !== null) {
        cuerpoConsolidado.innerHTML = paginacionOriginal;
        renderPaginationControls();
      }

      // 14) Quitar título insertado
      element.removeChild(titulo);

      // 15) Restaurar gráficas y canvases
      plotlyImgs.forEach(({ img, div }) => img.parentNode.replaceChild(div, img));
      canvasImgs.forEach(({ img, canvas }) => img.parentNode.replaceChild(canvas, img));
    });
  }, 1000);
}

// Ajustar tamaño de fuente de la tabla consolidada antes del PDF
document.addEventListener("DOMContentLoaded", () => {
  const tabla = document.querySelector("#tabContentTodasMaterias table");
  if (tabla) {
    tabla.style.fontSize = "9px";
  }
});



// Antes de llamar a html2pdfS
let tabla = element.querySelector("table");
let fontSizeOriginal = tabla.style.fontSize;
tabla.style.fontSize = "9px"; // o menos si es necesario

// ----------------------------------------------------
// 15) Generar reporte completo
// ----------------------------------------------------
function generarReportePDF() {
  setTimeout(() => {
    const element = document.getElementById("reporte");
    if (!element) return;
    html2pdf()
      .from(element)
      .set({
        margin: 1,
        filename: "ReporteCompleto.pdf",
        image: { type: "jpeg", quality: 0.98 },
        html2canvas: { scale: 2, scrollY: 0 },
        jsPDF: { unit: "in", format: "letter", orientation: "portrait" }
      })
      .save();
  }, 1000);
}

// ----------------------------------------------------
// 16) Render de paginación: mostrar página concreta
// ----------------------------------------------------
function renderTablePage(page) {
  if (page < 1) page = 1;
  if (page > totalPages) page = totalPages;
  currentPage = page;

  const cuerpoConsolidado = document.getElementById("cuerpoTablaConsolidado");
  if (!cuerpoConsolidado) return;
  cuerpoConsolidado.innerHTML = ""; // Limpiar filas previas

  const startIndex = (page - 1) * pageSize;
  const endIndex = page * pageSize;
  const alumnosEnPagina = listaAlumnos.slice(startIndex, endIndex);

  alumnosEnPagina.forEach((alumno, idx) => {
    const noFila = startIndex + idx + 1;
    let filaHTML = `<tr>`;
    filaHTML += `<td>${noFila}</td>`;
    filaHTML += `<td>${alumno.matricula}</td>`;
    filaHTML += `<td>${alumno.nombreCompleto}</td>`;

    materiasCargadas.forEach((idMat) => {
      const datosMat = alumno.calificaciones[idMat] || {};
      const ord1 = mostrarValor(datosMat.corte1);
      const ord2 = mostrarValor(datosMat.corte2);
      const ord3 = mostrarValor(datosMat.corte3);
      const promOrd = datosMat.calificacionOrdinaria || "";
      const rec1 = mostrarValor(datosMat.recuperacion1);
      const rec2 = mostrarValor(datosMat.recuperacion2);
      const rec3 = mostrarValor(datosMat.recuperacion3);
      const promRec = datosMat.calificacionOrdinariaR || "";

      filaHTML += `<td style="color:red;">${ord1}</td>`;
      filaHTML += `<td style="color:red;">${ord2}</td>`;
      filaHTML += `<td style="color:red;">${ord3}</td>`;
      filaHTML += `<td style="font-weight:bold;">${promOrd}</td>`;
      filaHTML += `<td>${rec1}</td>`;
      filaHTML += `<td>${rec2}</td>`;
      filaHTML += `<td>${rec3}</td>`;
      filaHTML += `<td style="font-weight:bold;">${promRec}</td>`;
    });

    const promGral = alumno.promedioGeneral || calcularPromedioGeneral(alumno.matricula, materiasCargadas.length);
    const nivelDesempeño = alumno.nivelDesempeño || determinarNivelDesempeño(promGral);
    filaHTML += `<td>${promGral}</td>`;
    filaHTML += `<td>${nivelDesempeño}</td>`;
    filaHTML += `</tr>`;

    cuerpoConsolidado.insertAdjacentHTML("beforeend", filaHTML);
  });

  // Después de dibujar las filas, refrescamos los botones de paginación
  renderPaginationControls();
}

// ----------------------------------------------------
// Ajusta el ancho de los scroll “dummy” superior e inferior
// ----------------------------------------------------
function ajustarScrollDummy() {
  const tableContainer    = document.getElementById("tableContainer");
  const dummyScrollTop    = document.getElementById("dummyScrollTop");
  const dummyScrollBottom = document.getElementById("dummyScrollBottom");

  if (tableContainer && dummyScrollTop && dummyScrollBottom) {
    const tableWidth = tableContainer.scrollWidth;
    dummyScrollTop.style.width    = tableWidth + "px";
    dummyScrollBottom.style.width = tableWidth + "px";
  }
}

// ----------------------------------------------------
// 16) Render de paginación: mostrar página concreta
// ----------------------------------------------------
function renderTablePage(page) {
  if (page < 1) page = 1;
  if (page > totalPages) page = totalPages;
  currentPage = page;

  const cuerpoConsolidado = document.getElementById("cuerpoTablaConsolidado");
  if (!cuerpoConsolidado) return;
  cuerpoConsolidado.innerHTML = ""; // Limpiar filas previas

  const startIndex = (page - 1) * pageSize;
  const endIndex = page * pageSize;
  const alumnosEnPagina = listaAlumnos.slice(startIndex, endIndex);

  alumnosEnPagina.forEach((alumno, idx) => {
    const noFila = startIndex + idx + 1;
    let filaHTML = `<tr>`;
    filaHTML += `<td>${noFila}</td>`;
    filaHTML += `<td>${alumno.matricula}</td>`;
    filaHTML += `<td>${alumno.nombreCompleto}</td>`;

    materiasCargadas.forEach((idMat) => {
      const datosMat = alumno.calificaciones[idMat] || {};
      const ord1 = mostrarValor(datosMat.corte1);
      const ord2 = mostrarValor(datosMat.corte2);
      const ord3 = mostrarValor(datosMat.corte3);
      const promOrd = datosMat.calificacionOrdinaria || "";
      const rec1 = mostrarValor(datosMat.recuperacion1);
      const rec2 = mostrarValor(datosMat.recuperacion2);
      const rec3 = mostrarValor(datosMat.recuperacion3);
      const promRec = datosMat.calificacionOrdinariaR || "";

      filaHTML += `<td style="color:red;">${ord1}</td>`;
      filaHTML += `<td style="color:red;">${ord2}</td>`;
      filaHTML += `<td style="color:red;">${ord3}</td>`;
      filaHTML += `<td style="font-weight:bold;">${promOrd}</td>`;
      filaHTML += `<td>${rec1}</td>`;
      filaHTML += `<td>${rec2}</td>`;
      filaHTML += `<td>${rec3}</td>`;
      filaHTML += `<td style="font-weight:bold;">${promRec}</td>`;
    });

    const promGral = alumno.promedioGeneral || calcularPromedioGeneral(alumno.matricula, materiasCargadas.length);
    const nivelDesempeño = alumno.nivelDesempeño || determinarNivelDesempeño(promGral);
    filaHTML += `<td>${promGral}</td>`;
    filaHTML += `<td>${nivelDesempeño}</td>`;
    filaHTML += `</tr>`;

    cuerpoConsolidado.insertAdjacentHTML("beforeend", filaHTML);
  });

  // — Pequeño retraso para que scrollWidth se calcule correctamente —
  setTimeout(() => {
    ajustarScrollDummy();
    renderPaginationControls();
  }, 50);
}

// ----------------------------------------------------
// 17) Renderización de los controles de paginación
// ----------------------------------------------------
function renderPaginationControls() {
  const paginationUl = document.getElementById("paginationControls");
  if (!paginationUl) return;
  paginationUl.innerHTML = ""; // Limpiar controles previos

  // Botón “Anterior”
  const prevLi = document.createElement("li");
  prevLi.classList.add("page-item");
  if (currentPage === 1) prevLi.classList.add("disabled");
  prevLi.innerHTML = `<a class="page-link" href="#" aria-label="Anterior">
                        <span aria-hidden="true">&laquo;</span>
                      </a>`;
  prevLi.addEventListener("click", (e) => {
    e.preventDefault();
    if (currentPage > 1) {
      renderTablePage(currentPage - 1);
    }
  });
  paginationUl.appendChild(prevLi);

  // Botones de páginas (1..totalPages)
  for (let p = 1; p <= totalPages; p++) {
    const li = document.createElement("li");
    li.classList.add("page-item");
    if (p === currentPage) li.classList.add("active");
    li.innerHTML = `<a class="page-link" href="#">${p}</a>`;
    li.addEventListener("click", (e) => {
      e.preventDefault();
      renderTablePage(p);
    });
    paginationUl.appendChild(li);
  }

  // Botón “Siguiente”
  const nextLi = document.createElement("li");
  nextLi.classList.add("page-item");
  if (currentPage === totalPages) nextLi.classList.add("disabled");
  nextLi.innerHTML = `<a class="page-link" href="#" aria-label="Siguiente">
                        <span aria-hidden="true">&raquo;</span>
                      </a>`;
  nextLi.addEventListener("click", (e) => {
    e.preventDefault();
    if (currentPage < totalPages) {
      renderTablePage(currentPage + 1);
    }
  });
  paginationUl.appendChild(nextLi);
}

// ----------------------------------------------------
// 18) DOMContentLoaded → listeners de scroll y demás
// ----------------------------------------------------
document.addEventListener("DOMContentLoaded", () => {
  // Scroll sincronizado (solo para “Todas las Materias”)
  const scrollTop = document.getElementById("scrollbarContainerTop");
  const scrollBottom = document.getElementById("scrollbarContainerBottom");
  const tableContainer = document.getElementById("tableContainer");

  if (scrollTop && scrollBottom && tableContainer) {
    scrollTop.addEventListener("scroll", () => {
      tableContainer.scrollLeft = scrollTop.scrollLeft;
      scrollBottom.scrollLeft = scrollTop.scrollLeft;
    });
    scrollBottom.addEventListener("scroll", () => {
      tableContainer.scrollLeft = scrollBottom.scrollLeft;
      scrollTop.scrollLeft = scrollBottom.scrollLeft;
    });
    tableContainer.addEventListener("scroll", () => {
      scrollTop.scrollLeft = tableContainer.scrollLeft;
      scrollBottom.scrollLeft = tableContainer.scrollLeft;
    });
    
  }

  const materiaSelect = document.getElementById("materiaSelectGrafica");
  if (materiaSelect) {
    materiaSelect.addEventListener("change", handleMateriaGraficaChange);
  }
});

//NEW GRAFICS


function initGraficasRendimiento(materiaId) {
  crearGraficaRendimientoCorte(1, materiaId, `plotlyCorte1_${materiaId}`);
  crearGraficaRendimientoCorte(2, materiaId, `plotlyCorte2_${materiaId}`);
  crearGraficaRendimientoCorte(3, materiaId, `plotlyCorte3_${materiaId}`);
  crearGraficaCalificacionesPorMateriaAvanzada(materiaId, `plotlyCalifDiv${materiaId}`);
}

// Cambia la firma de la función:
async function crearGraficaCalificacionesPorMateriaAvanzada(materiaId, containerId, modo = "completo") {
  try {
    const response = await fetch(`/por-materia/${materiaId}`);
    const calificaciones = await response.json();

    const alumnos = calificaciones.map(c => `${c.nombreAlumno} ${c.apellidoPaterno}`);
    const ordinarias = calificaciones.map(c => +c.calificacionOrdinaria || 0);
    const recuperaciones = calificaciones.map(c => +c.calificacionOrdinariaR || 0);

    const container = document.getElementById(containerId);
    if (!container) return;
    container.innerHTML = "";

    // Configuración para ambos modos
    const traceOrdinarias = {
      x: alumnos,
      y: ordinarias,
      name: 'Ordinaria',
      mode: 'lines+markers' + (modo === "completo" ? '+text' : ''),
      text: modo === "completo" ? ordinarias.map(v => v > 0 ? v : '') : undefined,
      textposition: 'top center',
      line: { color: '#1976D2', width: 3 },
      marker: { color: '#1976D2', size: 14 }
    };
    const traceRecuper = {
      x: alumnos,
      y: recuperaciones,
      name: 'Recuperación',
      mode: 'lines+markers' + (modo === "completo" ? '+text' : ''),
      text: modo === "completo" ? recuperaciones.map(v => v > 0 ? v : '') : undefined,
      textposition: 'bottom center',
      line: { color: '#D32F2F', width: 3 },
      marker: { color: '#D32F2F', size: 14 }
    };

    const layout = {
      title: {
        text: `<b>${materiaNombres[materiaId] || 'Materia'}</b>`,
        font: { size: 24 },
        xref: 'paper',
        x: 0.5,
        y: 0.9
      },
      plot_bgcolor: '#f5f5f5',
      paper_bgcolor: '#f5f5f5',
      xaxis: {
        title: '',
        tickangle: -45,
        showgrid: false,
        automargin: true,
        tickfont: {
          size: modo === "completo" ? 10 : 7 // Reduce el tamaño de letra en modo completo
        }
      },
      yaxis: {
        title: 'Calificación',
        range: [0, 110],
        dtick: 10,
        gridcolor: '#bbb'
      },
      legend: {
        orientation: 'h',
        y: -0.3,
        x: 0.5,
        xanchor: 'center'
      },
      margin: { t: 60, b: modo === "completo" ? 120 : 60, l: 60, r: 30 }
    };

    Plotly.newPlot(container, [traceOrdinarias, traceRecuper], layout, { responsive: true });

  } catch (e) { console.error(e); }
}

async function crearGraficaRendimientoCorte(corteId, materiaId, divId) {
  try {
    // 1. Trae todas las calificaciones de la materia
    const resp = await fetch(`/por-materia/${materiaId}`);
    const calificaciones = await resp.json();

    // 2. Selecciona el campo correcto según el corte
    const campo = corteId === 1 ? 'corte1' : corteId === 2 ? 'corte2' : 'corte3';

    // 3. Clasifica cada calificación en una categoría
    const categorias = ['Excelente', 'Notable', 'Bueno', 'Suficiente', 'Insuficiente'];
    const conteo = { Excelente: 0, Notable: 0, Bueno: 0, Suficiente: 0, Insuficiente: 0 };

    let total = 0, aprobados = 0, reprobados = 0;
    calificaciones.forEach(c => {
      const valor = parseFloat(c[campo]);
      if (isNaN(valor)) return;
      total++;
      if (valor >= 60) aprobados++; else reprobados++;
      if (valor >= 90) conteo.Excelente++;
      else if (valor >= 80) conteo.Notable++;
      else if (valor >= 70) conteo.Bueno++;
      else if (valor >= 60) conteo.Suficiente++;
      else conteo.Insuficiente++;
    });

    // 4. Prepara los datos para Plotly
    const cantidades = categorias.map(cat => conteo[cat]);
    const porcentajeAprob = total > 0 ? Math.round((aprobados / total) * 100) : 0;
    const porcentajeReprob = total > 0 ? Math.round((reprobados / total) * 100) : 0;

    const trace = {
      y: categorias,
      x: cantidades,
      type: 'bar',
      orientation: 'h',
      marker: {
        color: [
          '#4CAF50', // Excelente (verde)
          '#8BC34A', // Notable (verde claro)
          '#FFC107', // Bueno (amarillo)
          '#03A9F4', // Suficiente (azul)
          '#FF5722'  // Insuficiente (naranja/rojo)
        ]
      }
    };

    const layout = {
      title: `Desempeño ${corteId}er corte - ${materiaNombres[materiaId] || materiaId}<br>
      % Aprob = ${porcentajeAprob} % | % Reprob = ${porcentajeReprob} %`,
      margin: { t: 70, l: 100, r: 30, b: 40 },
      height: 260,
      xaxis: { title: 'Número de Alumnos', range: [0, Math.max(...cantidades, 5)] },
      yaxis: { title: 'Categoría', automargin: true }
    };

    const cont = document.getElementById(divId);
    if (!cont) return;
    cont.innerHTML = '';
    Plotly.newPlot(cont, [trace], layout, { responsive: true });
  } catch (err) {
    console.error(err);
  }
}

// ----------------------------------------------------
// Botones para cambiar vista de gráfica
// ----------------------------------------------------
let paginacionOriginal = null;
let cuerpoConsolidado = null;
let esTablaConsolidada = (idElemento === "tabContentTodasMaterias");

if (esTablaConsolidada && cuerpoConsolidado && paginacionOriginal !== null) {
  cuerpoConsolidado.innerHTML = paginacionOriginal;
  renderPaginationControls(); // Vuelve a mostrar la paginación
}


