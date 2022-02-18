(ns fen.core-test
  (:require [clojure.test :refer :all]
            [fen.core :as fen]))

(deftest start-position-fen-to-map-test
  (let [start-pos-fen "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-targets
                    halfmove-clock
                    fullmove-number]} (fen/fen->map start-pos-fen)]
    (is (= :white side-to-move))
    (is (= 1 fullmove-number))))

(deftest after-1-e4-fen-to-map-test
  (let [after-1-e4-fen "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-targets
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-fen)]
    (is (= :black side-to-move))
    (is (= 1 fullmove-number))))

(deftest after-1-e4-c5-fen-to-map-test
  (let [after-1-e4-c5-fen "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-targets
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-c5-fen)]
    (is (= :white side-to-move))
    (is (= 2 fullmove-number))))

(deftest after-1-e4-c5-2-Nf3-fen-to-map-test
  (let [after-1-e4-c5-2-Nf3-fen "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-targets
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-c5-2-Nf3-fen)]
    (is (= :black side-to-move))
    (is (= 2 fullmove-number))))
