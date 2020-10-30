# Roulette
Roulette API on Spring Boot.

# Main Features:

1: Endpoint to create new roulettes.
-Request method: POST.
-Path: /

2: Endpoint to open a roulette.
-Request method: PUT.
-Path: /{Roulette_ID}

3.1: Endpoint to bet a number.
-Request method: POST.
-Path: /bet/number/{RouletteID}

3.2: Endpoint to bet a color.
-Request method: POST.
-Path: /bet/color/{RouletteID}

4: Endpoint to close a bet.
-Request method: POST.
-Path: /close/{RouletteID}

5: Endpoint for list created roulettes.
-Request method: GET.
-Path: /
