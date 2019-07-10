import '@vaadin/vaadin-lumo-styles/typography.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/icons.js';
import '@vaadin/vaadin-lumo-styles/badge.js';
import '@vaadin/vaadin-lumo-styles/presets/compact.js';

// TODO: Replace with @CssModule once it's available
let id = 0;
const addComponentStyle = (componentName, css) => {
  const domModuleElement = document.createElement('dom-module');
  domModuleElement.setAttribute('theme-for', componentName);
  domModuleElement.id = `themable-component-style-${id++}`;

  const templateElement = document.createElement('template');
  domModuleElement.appendChild(templateElement);

  const styleElement = document.createElement('style');

  styleElement.innerHTML = css;
  templateElement.content.appendChild(styleElement);
  document.head.appendChild(domModuleElement);
}

const addCustomStyle = (include, css) => {
  const customStyleElement = document.createElement('custom-style');
  const styleElement = document.createElement('style');
  if (include) {
    styleElement.setAttribute('include', include);
  }

  styleElement.innerHTML = css;

  customStyleElement.appendChild(styleElement);
  document.head.appendChild(customStyleElement);
}

addComponentStyle('vaadin-text-field', `
  :host([theme="custom"]) [part="input-field"],
  :host([theme="custom"]) [part="input-field"] {
    background-color: transparent;
  }

  :host([class='large']) [part~="value"] {
    font-size: var(--lumo-font-size-xxl);
    --lumo-text-field-size: var(--lumo-size-xl);
  }
`);

addComponentStyle('vaadin-form-item', `
  :host {
    --vaadin-form-item-label-width: 0;
    --vaadin-form-item-label-spacing: 0;
    --vaadin-form-item-row-spacing: 20px;
  }
`);

addComponentStyle('vaadin-button', `
  :host(.delete-button) {
    padding: var(--lumo-space-xs) 0;
    margin: 0;
  }

  :host(.delete-button) ::slotted(*) {
    color: var(--lumo-contrast-60pct);
  }
`);

addCustomStyle('lumo-badge', `
  html {
    height: 100%;
  }

  body {
    margin: 0;
    height: 100%;
    font-family: var(--lumo-font-family);
    --vaadin-board-width-small: 1120px;
    --vaadin-board-width-medium: 1280px;
  }

  #container {
    display: flex;
    height: 100%;
    flex-direction: column;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
  }

  .controls-line {
    display: flex;
    flex: none;
    align-items: center;
    background: var(--lumo-contrast-5pct);
    flex-wrap: wrap;
  }

  .controls-line:first-of-type vaadin-button:not(:last-child) {
    margin-right: var(--lumo-space-s);
  }

  .controls-line-buttons {
    padding: 0 var(--lumo-space-s);
  }

  vaadin-board {
    flex-shrink: 0;
    width: auto !important;
    padding: 0 var(--lumo-space-m);
    margin-bottom: var(--lumo-space-xl);
  }

  vaadin-form-layout {
    margin-right: var(--lumo-space-xl);
  }

  @media (max-width: 1120px) {
    vaadin-form-layout {
      margin: 0;
    }
  }

  vaadin-rich-text-editor {
    min-height: 30vh;
    max-height: 50vh;
    margin: var(--lumo-space-m) 0;
  }

  vaadin-grid-pro {
    min-height: calc(var(--lumo-size-xl) * 6);
  }

  .icon-eur::before,
  .icon-usd::before,
  .icon-gbp::before {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    margin: 0 var(--lumo-space-m);
    color: var(--lumo-base-color);
    height: calc(var(--lumo-size-m) / 2);
    width: calc(var(--lumo-size-m) / 2);
    border-radius: 50%;
  }

  .icon-eur::before {
    content: '€';
    background-color: var(--lumo-primary-color);
  }

  .icon-usd::before  {
    content: '$';
    background-color: var(--lumo-success-color);
  }

  .icon-gbp::before  {
    content: '£';
    background-color: var(--lumo-error-color);
  }

  .invoice-details {
    flex: 1;
    display: flex;
    align-items: center;
    flex-basis: auto;
  }

  .invoice-details span.small {
    font-size: smaller;
    color: var(--lumo-secondary-text-color);
  }

  .invoice-details span {
    margin-right: var(--lumo-space-m);
  }

  #add-transaction span, #add-line span {
    display: flex;
    align-items: center;
  }

  #add-transaction span::before, #add-line span::before {
    font-family: 'lumo-icons';
    font-size: var(--lumo-size-s);
    content: var(--lumo-icons-plus);
  }

  .currency-selector {
    width: 90px;
  }

  .footer {
    padding-right: var(--lumo-space-m);
    font-size: var(--lumo-font-size-m);
    color: var(--lumo-body-text-color);
    justify-content: flex-end;
  }

  .footer .total {
    color: var(--lumo-header-text-color);
  }
`);
