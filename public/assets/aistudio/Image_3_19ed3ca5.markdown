---
name: Terracotta & Tide
colors:
  surface: '#f8f9ff'
  surface-dim: '#ccdbf4'
  surface-bright: '#f8f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eff4ff'
  surface-container: '#e6eeff'
  surface-container-high: '#dde9ff'
  surface-container-highest: '#d5e3fd'
  on-surface: '#0d1c2f'
  on-surface-variant: '#59413c'
  inverse-surface: '#233144'
  inverse-on-surface: '#ebf1ff'
  outline: '#8d716a'
  outline-variant: '#e1bfb8'
  surface-tint: '#ad3217'
  primary: '#aa3015'
  on-primary: '#ffffff'
  primary-container: '#cc482b'
  on-primary-container: '#fffbff'
  inverse-primary: '#ffb4a4'
  secondary: '#006a61'
  on-secondary: '#ffffff'
  secondary-container: '#86f2e4'
  on-secondary-container: '#006f66'
  tertiary: '#a83214'
  on-tertiary: '#ffffff'
  tertiary-container: '#ca4a2a'
  on-tertiary-container: '#fffbff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdad3'
  primary-fixed-dim: '#ffb4a4'
  on-primary-fixed: '#3d0600'
  on-primary-fixed-variant: '#8b1901'
  secondary-fixed: '#89f5e7'
  secondary-fixed-dim: '#6bd8cb'
  on-secondary-fixed: '#00201d'
  on-secondary-fixed-variant: '#005049'
  tertiary-fixed: '#ffdad2'
  tertiary-fixed-dim: '#ffb4a2'
  on-tertiary-fixed: '#3c0700'
  on-tertiary-fixed-variant: '#8a1c00'
  background: '#f8f9ff'
  on-background: '#0d1c2f'
  surface-variant: '#d5e3fd'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '800'
    lineHeight: 48px
    letterSpacing: -0.03em
  display-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 40px
    letterSpacing: -0.025em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 30px
    letterSpacing: -0.015em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 26px
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 26px
    letterSpacing: -0.005em
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 22px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 13px
    fontWeight: '400'
    lineHeight: 18px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 14px
    letterSpacing: 0.05em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-sm: 0.75rem
  margin: 1.25rem
  margin-sm: 1rem
  margin-lg: 1.5rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system expresses the sensory exhilaration of exploratory travel paired with absolute hospitality and effortless poise. It fuses modern minimalism with an editorial, tactile warmth: sun-drenched earth tones grounded by cool maritime balance. It intentionally rejects the sterile, clinical feel of standard travel utilities in favor of an aspirational, curated journal aesthetic.

The target audience consists of design-conscious voyagers, design students studying premier mobile UX patterns, and discerning travelers seeking clarity, tranquility, and delight. The interface must evoke warmth, precision, wanderlust, and confidence. Interactions should feel deliberate, physically responsive, and unhurried.

## Colors

The palette balances warm terracotta clay with oceanic serenity and mineral fundamentals:

- **Primary (`#E05638`) & Primary Accent (`#F06543`)**: Sunset terracotta and radiant coral. Used deliberately for high-intent actions (primary booking triggers, active trip states, selected dates, hero indicators).
- **Secondary (`#0D9488`)**: Deep tidepool teal. Provides intellectual balance, used for reassurance markers, verified badges, amenity tags, nature-focused highlights, and complementary status markers.
- **Neutrals & Typography (`#0F172A`, `#334155`, `#64748B`)**: Layered deep slate tones providing crisp contrast without harsh pure blacks. Primary headlines sit at `#0F172A`, while supporting copy rests at `#334155`.
- **Surfaces & Backgrounds (`#FAF8F5`, `#F1EFEA`, `#FFFFFF`)**: Warm sand canvas (`#FAF8F5`) acts as the root background, soft travertine (`#F1EFEA`) provides inset wells and secondary grouping, while pure linen white (`#FFFFFF`) elevates primary actionable cards.

## Typography

The typography relies on Plus Jakarta Sans across all roles to achieve a warm geometric modernism with open apertures, distinct terminal cuts, and friendly proportions.

- **Headlines & Display**: Set with tighter tracking (`-0.01em` to `-0.03em`) and substantial weight (`700` to `800`) to anchor editorial hierarchy and guide scanning eyes naturally down the canvas.
- **Body & Content**: Optimized with comfortable, open line heights (`1.5` to `1.625`) to guarantee effortless readability during flight, train, or outdoor transit conditions.
- **Labels & Metadata**: Set slightly tracked out (`0.01em` to `0.05em`) in medium-to-bold weights to maintain legibility at glanceable micro-scales on dense booking screens.

## Layout & Spacing

The layout is built for high-touch mobile ergonomics using a 4-column mobile grid transitioning to an 8-column layout on compact tablets:

- **Horizontal Bleed & Margins**: Default outer page gutters sit at `1.25rem` (20px) on standard viewports, dropping to `1rem` on narrow devices (<375px) to preserve content width.
- **Rhythm & Flow**: Vertical stacks strictly employ multiples of 4px and 8px. Related metadata pairs sit within `space-xs` (4px) to `space-sm` (8px). Structural cards and content sections sit separated by `space-lg` (24px) or `space-xl` (32px).
- **Touch-First Guardrails**: All interactive hit areas must satisfy a strict minimum target bounding box of 44x44px, regardless of the visual footprint of the enclosed glyph or icon.

## Elevation & Depth

Visual depth is achieved through warm, diffused ambient shadows and tonal surface shifts rather than high-contrast structural borders:

- **Base Floor (`#FAF8F5`)**: Level 0 canvas receiving all passive layout elements.
- **Flat Elevated Containers (`#FFFFFF`)**: Level 1 surfaces (cards, collection items, search cards) elevated by subtle dual-pass amber-tinted shadows:
  - Ambient: `0 4px 20px -2px rgba(51, 65, 85, 0.05)`
  - Direct: `0 1px 3px 0 rgba(224, 86, 56, 0.04)`
  - Edge definition: 1px hairline border rendered with `rgba(15, 23, 42, 0.04)`.
- **Floating Controls & Modals**: Level 2 surfaces (floating bottom navigation bars, quick-action filters, sticky pricing bars) use deep ambient diffusion:
  - Blur: `0 12px 32px -4px rgba(15, 23, 42, 0.08), 0 4px 12px -2px rgba(15, 23, 42, 0.03)`
  - Glass backdrop: Where applicable on mobile navigation, apply `backdrop-filter: blur(16px)` over a 90% opaque sand surface (`rgba(250, 248, 245, 0.90)`).

## Shapes

The design system embraces an inviting, soft geometry that reflects smooth sea glass and wind-shaped stone:

- **Cards & Primary Modules**: Standard cards and elevated content tiles utilize `1rem` (16px) to `1.5rem` (24px) corner radii (`rounded-lg` and `rounded-xl`).
- **Interactive Controls & Inputs**: Text fields, search bars, and primary buttons conform to `0.75rem` to `1rem` corner radii, ensuring comfortable finger contact.
- **Tags, Pills, & Micro-actions**: Status badges, date chips, and image carousel paginators use fully rounded pill geometries (`9999px`) to immediately convey actionable toggle states.

## Components

### Buttons
- **Primary Action**: Solid terracotta fill (`#E05638`), crisp white text, min-height 52px, horizontal padding `space-lg`, border-radius 16px. Active/pressed state scales subtly (`scale(0.98)`) and shifts toward `#F06543`.
- **Secondary Action**: Warm sand/stone fill (`#F1EFEA`), slate text (`#0F172A`), zero border, matching 52px height.
- **Ghost/Tertiary Action**: Transparent background with teal (`#0D9488`) or slate text, intended for inline links and dismiss triggers.

### Chips & Filters
- **Default State**: Soft stone background (`#F1EFEA`), `#334155` text, 36px height, rounded-full, 14px horizontal padding.
- **Selected State**: Deep slate (`#0F172A`) or primary terracotta fill (`#E05638`) with white text and a soft color-matched glow shadow.

### Input Fields & Search Bars
- **Surface**: Pure white (`#FFFFFF`) with 1px hairline border in `rgba(15, 23, 42, 0.08)`.
- **Height & Spacing**: 54px height with leading icon slots (20px icons in `#64748B`), `space-md` text inset, and explicit floating label support.
- **Focus State**: Terracotta outline ring (`2px solid #E05638`) with neutral tinted glow.

### Cards & Itinerary Modules
- **Structure**: White base (`#FFFFFF`), 20px padding (`space-lg`), 20px radius (`rounded-xl`), ambient shadow.
- **Media Presentation**: Aspect-ratio locked image containers (16:10 or 4:3) with 14px internal border radius, subtle dark gradient scrims for top-right heart/bookmark badges and bottom-left pricing overlays.

### Status Badges
- **Verified / Eco / Maritime**: Light teal tint (`rgba(13, 148, 136, 0.10)`), teal text (`#0D9488`), uppercase `label-sm` tracking, 6px horizontal padding, 4px vertical padding, rounded-full.
- **Urgency / Peak Season**: Terracotta tint (`rgba(224, 86, 56, 0.10)`), terracotta text (`#E05638`).

### Navigation Patterns
- **Floating Island Bar**: 64px height, suspended 16px above bottom safe area, 24px corner radius, frosted sand finish with active icon indicators underlined by a 4px coral dot.