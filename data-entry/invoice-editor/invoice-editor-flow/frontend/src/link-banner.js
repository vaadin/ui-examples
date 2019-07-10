import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-icon/iron-icon.js';

class LinkBanner extends PolymerElement {
  static get is() { return 'link-banner'; }

  static get template() {
    return html`
    <style>
      :host {
        font-family: "Montserrat", -apple-system, BlinkMacSystemFont, "Roboto", "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
        background: #26282B;
        color: #fff;
        display: flex;
        padding: 10px 24px;
        align-items: center;
        flex-shrink: 0;
        box-shadow: 0px 2px 18px rgba(0, 0, 0, 0.3), 0px 3px 8px rgba(0, 0, 0, 0.2);
        position: -webkit-sticky;
        position: sticky;
        top: 0;
        z-index: 100;
      }

      :host([hidden]) {
        display: none;
      }

      #explainer {
        flex: 1;
        font-style: normal;
        font-weight: 500;
        font-size: 15px;
        margin: 10px 0;
        font-weight: 300;
      }

      #banner-links {
        display: flex;
        align-items: center;
      }

      #source-link {
        background: #00B2F2;
        padding: 11px 15px;
        color: #fff;
        border-radius: 2em;
        text-decoration: none;
        margin-right: 32px;
        display: inline-flex;
        align-items: center;
        font-size: 13px;
        letter-spacing: 1.3px;
        white-space: nowrap;
      }

      #source-link span {
        margin: 0 10px;
      }

      #examples-link {
        color: #00B2F2;
        text-decoration: none;
        position: relative;
        margin-right: 22px;
        font-size: 13px;
        letter-spacing: 1.3px;
        text-align: center;
      }

      #examples-link::after {
        content: "";
        position: absolute;
        height: 2px;
        left: 0;
        right: 0;
        top: calc(50% - 2px/2 + 15px);
        background: #00B2F2;
      }

      #hide-icon {
        cursor: pointer;
        width: 34px;
        height: 34px;
        flex-shrink: 0;
      }

      @media (max-width: 750px) {
        :host {
          padding: 10px;
          flex-direction: column;
        }

        #source-link {
          padding: 7px 4px;
          margin-right: 5px;
        }

        #examples-link {
          margin-right: 5px;
        }
      }
    </style>
    <div id="explainer">Inspect the source and learn to build the UI using Vaadin components</div>
    <div id="banner-links">
      <a href="https://github.com/vaadin/ui-examples/tree/master/data-entry/invoice-editor" id="source-link">
        <svg height="22" class="octicon octicon-mark-github" viewBox="0 0 16 16" version="1.1" width="32" aria-hidden="true">
          <path fill="#fff" fill-rule="evenodd" d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"></path>
        </svg>
        <span>VIEW SOURCE</span>
      </a>
      <a href="https://vaadin.com/components/demo-apps/data-entry" id="examples-link">MORE APP EXAMPLES</a>
      <iron-icon id="hide-icon" tabindex="0" icon="lumo:cross" slot="prefix" on-click="hide"></iron-icon>
    </div>`;
  }

  hide() {
    this.hidden = true;
  }
}

window.customElements.define(LinkBanner.is, LinkBanner);

