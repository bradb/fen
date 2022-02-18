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
    (is (= 1 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

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
    (is (= 1 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

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
    (is (= 2 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

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
    (is (= 2 fullmove-number))
    (is (= 1 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

(deftest white-cant-castle-kingside-test
  (let [fen "rnbqkbnr/pppp1ppp/8/4p3/7P/8/PPPPPPPR/RNBQKBN1 b Qkq - 1 2"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (false? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

(deftest white-cant-castle-either-side-test
  (let [fen "rnbqkbnr/pp3ppp/8/2ppp3/P6P/8/RPPPPPPR/1NBQKBN1 b kq - 1 4"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (false? allow-white-kingside-castle?))
    (is (false? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

(deftest black-cant-castle-queenside-test
  (let [fen "1nbqkbnr/rppppppp/8/p7/3PP3/8/PPP2PPP/RNBQKBNR w KQk - 1 3"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (false? allow-black-queenside-castle?))))

(deftest two-kings-two-pawns-test
  (let [fen "4k3/8/8/8/8/8/4P3/4K3 w - - 5 39"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-targets
                    halfmove-clock
                    fullmove-number]} (fen/fen->map fen)]
    (is (false? allow-white-kingside-castle?))
    (is (false? allow-white-queenside-castle?))
    (is (false? allow-black-kingside-castle?))
    (is (false? allow-black-queenside-castle?))

    (is (= :white side-to-move))
    (is (= 39 fullmove-number))
    (is (= 5 halfmove-clock))))
