# spheres-sounds

A [re-frame](https://github.com/Day8/re-frame) application.

Exploring the characteristics of our solar system's bodies with sound.

## Production:

You will find a working example: [here.](https://github.com/manandearth/spheres-sounds)

### Background:

Somehow derived from a rather limited world view, one that dominated Europe in its dark middle ages, 
here is yet another exploration to sound and the planets. A modern  *Harmony of the shpheres*...

### Work in progress...
This work is in the making; features are being created and added to make this instrument useful.
Feel free to add suggestions and issues [here](https://github.com/manandearth/spheres-sounds/issues)

### Use:

- Some of the different attributes that can be explore here are demontrated in my [spheres-rf-ha app](https://manandearth.github.io/spheres-rf-ha/). Have a play with the visualisation there to get an idea of the relations between the differrent bodies. then:
 
- Select a system from the top bar, ( the bodies are those bodies in the solar system that have satelites).
- Toggle *on/off* the bodies included in the next menu.
- TBC

## Development Mode

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

### Compile css:

Compile css file once.

```
lein garden once
```

Automatically recompile css file on change.

```
lein garden auto
```

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
