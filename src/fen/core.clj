(ns fen.core
  (:require [clojure.string :as str]))

;; todo
;; - add specs

(defn fen->map
  "Convert a FEN string into a board representation.

  Board representation is a map with the following keys:

  :fen/board - A one-dimensional vector describing the board state,
               from a1 (index 0) to h8 (index 63)
  :fen/allow-white-kingside-castle? - A boolean
  :fen/allow-white-queenside-castle? - A boolean
  :fen/allow-black-kingside-castle? - A boolean
  :fen/allow-black-queenside-castle? - A boolean
  :fen/side-to-move - :white or :black
  :fen/en-passant-targets - A coll of squares
  :fen/halfmove-clock - An integer
  :fen/fullmove-number - An integer"
  [fen]
  (let [[pieces
         active-colour
         castling-availability
         en-passant-targets
         halfmove-clock
         fullmove-number]
        (str/split fen #" ")]
    {:fen/side-to-move (case active-colour
                         "w" :white
                         "b" :black
                         (throw (str "don't know how to parse active colour " active-colour)))
     :fen/fullmove-number (Integer/parseInt fullmove-number)}))

(defn map->fen
  "Convert a board map into a FEN string.

  See `fen->board` for the format of the map."
  [board])
