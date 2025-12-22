# Proyecto Intermodular - Nombre del Proyecto
Catherine Pérez Lage 1º Desarrollo de Aplicaciones Web 
## Descripción
Este es el github de mi proyecto intermodular 
## Estructura del Proyecto
- `database/`: Scripts y herramientas para la gestión de la base de datos.
- `docs/`: Documentación del proyecto.
- `src/`: Código fuente de la aplicación.

## Unidad 6 proyecto HTML + CSS
-   He decidido hacer un maquetado de tarjetas para que el diseño de la web fuera lo más consistente posible. 

-   A nivel de historia de colores, diseñé un logo en Canva y a partir de esos colores cree el diseño en armonía.

-   A lo largo de este proceso me he encontrado con varias dificultades, la que más el manejo de la flexbox, cuando pensaba que ponía una cosa en su sitio el resto se descolocaban. Terminando el proyecto y haciendo  las media query me di cuenta de que no se estaban aplicando, tras una larga (muy larga) investigación caí en la cuenta de que en el head del HTML no había especificado el viewport. 

-   En el estilado del formulario tuve un debate con las pseudoclases :valid/:invalid. Al abrir el formulario aparecía el input directamente en :invalid en los campos required (sin haberles hecho focus ni nada). Decidí omitir el paso de :valid/:invalid porque si no el color del :focus no se mostraba nunca.
